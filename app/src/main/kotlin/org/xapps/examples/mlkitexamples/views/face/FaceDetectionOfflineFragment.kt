package org.xapps.examples.mlkitexamples.views.face

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.face.FaceDetection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.xapps.examples.mlkitexamples.views.RecognitionFragment
import org.xapps.examples.mlkitexamples.views.utils.Detection
import javax.inject.Inject


@AndroidEntryPoint
class FaceDetectionOfflineFragment @Inject constructor() : RecognitionFragment() {

    private val faceDetection: FaceDetectionOfflineAnalyzer by lazy {
        FaceDetectionOfflineAnalyzer(FaceDetection.getClient())
    }

    override fun analyzer(): ImageAnalysis.Analyzer = faceDetection

    override suspend fun detections(): Flow<List<Detection>> = faceDetection.detections()

    override fun data(info: List<Detection>): String? {
        return null
    }

}