package pl.lgawin.safelycamera.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.MediaStore

class ExternalActivityCameraDispatcher(
    private val activity: Activity,
    private val requestCode: Int,
    private val onResult: (Intent) -> Unit,
    private val onCancel: () -> Unit
) : CameraDispatcher {

    override fun dispatchTakePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            activity.startActivityForResult(takePictureIntent, requestCode)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun dispatchResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> onResult(data!!)
            Activity.RESULT_CANCELED -> onCancel()
            else -> throw IllegalArgumentException("Unsupported result code: $resultCode")
        }
    }
}
