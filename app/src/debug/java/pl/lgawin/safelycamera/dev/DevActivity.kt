package pl.lgawin.safelycamera.dev

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.camera.ExternalActivityCameraDispatcher
import pl.lgawin.safelycamera.databinding.ActivityDevBinding
import pl.lgawin.safelycamera.toast

class DevActivity : AppCompatActivity() {

    private val viewModel by viewModels<DevActivityViewModel>()

    private val cameraDispatcher by lazy {
        ExternalActivityCameraDispatcher(
            this,
            REQUEST_IMAGE_CAPTURE,
            onResult = { viewModel.bitmap = it },
            onCancel = { toast("RESULT_CANCELED") },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDevBinding>(this, R.layout.activity_dev).apply {
            vm = viewModel
            takePicture.setOnClickListener { cameraDispatcher.dispatchTakePicture() }
            lifecycleOwner = this@DevActivity
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> cameraDispatcher.dispatchResult(resultCode, data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 5362
    }
}
