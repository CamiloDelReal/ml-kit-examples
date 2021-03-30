package org.xapps.examples.mlkitexamples.views.barcode

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.xapps.examples.mlkitexamples.views.utils.Detection
import timber.log.Timber


@OptIn(ExperimentalCoroutinesApi::class)
class BarcodeScannerOfflineAnalyzer(private val scanner: BarcodeScanner) : ImageAnalysis.Analyzer {

    private val detections = MutableSharedFlow<List<Detection>>(replay = 1)

    fun detections(): Flow<List<Detection>> = detections.asSharedFlow()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val detectionsList = mutableListOf<Detection>()
                    for (barcode in barcodes) {
                        detectionsList.add(
                            Detection(
                                tag = barcode.rawValue,
                                boundingBox = barcode.boundingBox,
                                sourceWidth = image.width,
                                sourceHeight = image.height,
                                sourceRotation = image.rotationDegrees,
                                data = barcode
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