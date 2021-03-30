package org.xapps.examples.mlkitexamples.views.imagelabeling

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.xapps.examples.mlkitexamples.views.utils.Detection
import timber.log.Timber


@OptIn(ExperimentalCoroutinesApi::class)
class ImageLabelingOfflineAnalyzer(private val imageLabeler: ImageLabeler) :
    ImageAnalysis.Analyzer {

    private val detections = MutableSharedFlow<List<Detection>>(replay = 1)

    fun detections(): Flow<List<Detection>> = detections.asSharedFlow()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            imageLabeler.process(image)
                .addOnSuccessListener { imageLabels ->
                    val detectionsList = mutableListOf<Detection>()
                    for (imageLabel in imageLabels) {
                        detectionsList.add(
                            Detection(
                                tag = imageLabel.text,
                                sourceWidth = image.width,
                                sourceHeight = image.height,
                                sourceRotation = image.rotationDegrees,
                                data = imageLabel
                            )
                        )
                    }
                    detections.tryEmit(detectionsList)
                }
                .addOnFailureListener { e ->
                    Timber.e(e, "Error captured while precessing image")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

}