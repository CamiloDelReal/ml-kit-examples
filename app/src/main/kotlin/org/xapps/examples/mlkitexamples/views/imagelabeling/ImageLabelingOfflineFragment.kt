package org.xapps.examples.mlkitexamples.views.imagelabeling

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import org.xapps.examples.mlkitexamples.views.RecognitionFragment
import org.xapps.examples.mlkitexamples.views.utils.Detection
import javax.inject.Inject


@AndroidEntryPoint
class ImageLabelingOfflineFragment @Inject constructor() : RecognitionFragment() {

    private val imageLabeling: ImageLabelingOfflineAnalyzer by lazy {
        ImageLabelingOfflineAnalyzer(ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS))
    }

    override fun analyzer(): ImageAnalysis.Analyzer = imageLabeling

    override suspend fun detections(): Flow<List<Detection>> = imageLabeling.detections()

    override fun data(info: List<Detection>): String {
        return info.map { it.tag as String }.joinToString(", ")
    }

}