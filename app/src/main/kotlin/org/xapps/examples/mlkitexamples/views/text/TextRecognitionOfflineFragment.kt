package org.xapps.examples.mlkitexamples.views.text

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.text.TextRecognition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.xapps.examples.mlkitexamples.views.RecognitionFragment
import org.xapps.examples.mlkitexamples.views.utils.Detection
import javax.inject.Inject


@AndroidEntryPoint
class TextRecognitionOfflineFragment @Inject constructor() : RecognitionFragment() {

    private val textRecognition: TextRecognitionOfflineAnalyzer by lazy {
        TextRecognitionOfflineAnalyzer(TextRecognition.getClient())
    }

    override fun analyzer(): ImageAnalysis.Analyzer = textRecognition

    override suspend fun detections(): Flow<List<Detection>> = textRecognition.detections()

    override fun data(info: List<Detection>): String {
        return info.map { it.tag as String }.joinToString(", ")
    }
}