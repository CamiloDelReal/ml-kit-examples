package org.xapps.examples.mlkitexamples.views.text

import androidx.camera.core.ImageAnalysis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.xapps.examples.mlkitexamples.views.RecognitionFragment
import org.xapps.examples.mlkitexamples.views.utils.Detection
import javax.inject.Inject


@AndroidEntryPoint
class TextRecognitionOnlineFragment @Inject constructor(): RecognitionFragment() {

    override fun analyzer(): ImageAnalysis.Analyzer {
        TODO("Not yet implemented")
    }

    override suspend fun detections(): Flow<List<Detection>> {
        TODO("Not yet implemented")
    }

    override fun data(info: List<Detection>): String? {
        TODO("Not yet implemented")
    }
}