package org.xapps.examples.mlkitexamples.views.barcode

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.BarcodeScanning
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.xapps.examples.mlkitexamples.views.RecognitionFragment
import org.xapps.examples.mlkitexamples.views.utils.Detection
import javax.inject.Inject


@AndroidEntryPoint
class BarcodeScannerOfflineFragment @Inject constructor() : RecognitionFragment() {

    private val barcodeScanner: BarcodeScannerOfflineAnalyzer by lazy {
        BarcodeScannerOfflineAnalyzer(BarcodeScanning.getClient())
    }

    override fun analyzer(): ImageAnalysis.Analyzer = barcodeScanner

    override suspend fun detections(): Flow<List<Detection>> = barcodeScanner.detections()

    override fun data(info: List<Detection>): String {
        return info.map { it.tag as String }.joinToString(", ")
    }

}