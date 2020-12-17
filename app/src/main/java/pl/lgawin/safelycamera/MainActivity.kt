package pl.lgawin.safelycamera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import pl.lgawin.safelycamera.camera.ExternalActivityCameraDispatcher
import pl.lgawin.safelycamera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

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
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            vm = viewModel
            takePicture.setOnClickListener { cameraDispatcher.dispatchTakePicture() }
            lifecycleOwner = this@MainActivity
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

private fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
