package org.xapps.examples.mlkitexamples.views.text

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.xapps.examples.mlkitexamples.views.utils.Detection
import timber.log.Timber


@OptIn(ExperimentalCoroutinesApi::class)
class TextRecognitionOfflineAnalyzer(private val recognizer: TextRecognizer) :
    ImageAnalysis.Analyzer {

    private val detections = MutableSharedFlow<List<Detection>>(replay = 1)

    fun detections(): Flow<List<Detection>> = detections.asSharedFlow()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val detectionsList = mutableListOf<Detection>()
                    for (block in visionText.textBlocks) {
                        detectionsList.add(
                            Detection(
                                tag = block.text,
                                boundingBox = block.boundingBox,
                                sourceWidth = image.width,
                                sourceHeight = image.height,
                                sourceRotation = image.rotationDegrees,
                                data = block
                            )
                        )
                    }
                    detections.tryEmit(detectionsList)
                }
                .addOnFailureListener { e ->
                    Timber.e(e, "Error captured while processing image")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

}