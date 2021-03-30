package org.xapps.examples.mlkitexamples.views.face

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.xapps.examples.mlkitexamples.views.utils.Detection
import timber.log.Timber


@OptIn(ExperimentalCoroutinesApi::class)
class FaceDetectionOfflineAnalyzer(private val detector: FaceDetector) : ImageAnalysis.Analyzer {

    private val detections = MutableSharedFlow<List<Detection>>(replay = 1)

    fun detections(): Flow<List<Detection>> = detections.asSharedFlow()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            Timber.i("Before send image to process")
            detector.process(image)
                .addOnSuccessListener { faces ->
                    Timber.i("Success listener with count ${faces.size}")
                    val detectionsList = mutableListOf<Detection>()
                    for (face in faces) {
                        Timber.e("Face detected inside ${face.boundingBox}")
                        detectionsList.add(
                            Detection(
                                boundingBox = face.boundingBox,
                                sourceWidth = image.width,
                                sourceHeight = image.height,
                                sourceRotation = image.rotationDegrees,
                                data = face
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