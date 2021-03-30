package org.xapps.examples.mlkitexamples.views

import android.Manifest
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.xapps.examples.mlkitexamples.R
import org.xapps.examples.mlkitexamples.databinding.FragmentPreviewAndDetectionViewBinding
import org.xapps.examples.mlkitexamples.views.utils.Detection
import timber.log.Timber
import java.util.concurrent.Executors


abstract class RecognitionFragment : Fragment() {

    private lateinit var bindings: FragmentPreviewAndDetectionViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentPreviewAndDetectionViewBinding.inflate(layoutInflater)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.apply {
                        if (areAllPermissionsGranted()) {
                            Timber.i(getString(R.string.permissions_granted))
                            setup()
                        } else {
                            Timber.w(getString(R.string.permissions_not_granted))
                            requireActivity().finish()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Timber.e(getString(R.string.error_requiresting_permissions, it?.name))
            }
            .check()
    }

    private fun setup() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(bindings.previewView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val displayMetric = DisplayMetrics().also { bindings.previewView.display.getRealMetrics(it) }
            val previewSize = Size(displayMetric.widthPixels, displayMetric.heightPixels)
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(requireActivity().window.decorView.display.rotation)
                .setTargetResolution(previewSize)
                .build()

            lifecycleScope.launchWhenResumed {
                detections().collect { detections ->
                    Timber.i("Detections received")
                    bindings.detectionView.detections = detections
                    data(detections)?.also {
                        bindings.txvData.isVisible = it.isNotEmpty()
                        bindings.txvData.text = it
                    }
                }
            }
            imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor(),  analyzer())

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)

            } catch (e: Exception) {
                Timber.e(e, getString(R.string.error_binding_camera_to_lifecycle))
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    protected abstract fun analyzer(): ImageAnalysis.Analyzer
    protected abstract suspend fun detections(): Flow<List<Detection>>
    protected abstract fun data(info: List<Detection>): String?
}