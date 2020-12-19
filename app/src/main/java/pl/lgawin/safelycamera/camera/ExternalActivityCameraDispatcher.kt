package pl.lgawin.safelycamera.camera

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import pl.lgawin.safelycamera.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ExternalActivityCameraDispatcher(
    private val context: Context,
    private val onResult: (Bitmap?) -> Unit,
    private val onCancel: () -> Unit,
    private val startActivityForResult: (Intent) -> Unit
) : CameraDispatcher {

    private var file: File? = null

    override fun dispatchTakePicture(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("J${timeStamp}_", ".jpg", storageDir)
        file?.let {
            val photoUri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", it)
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            try {
                startActivityForResult(takePictureIntent)
                return it
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }
        return null
    }

    override fun dispatchResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            RESULT_OK -> internalSuccess(data)
            RESULT_CANCELED -> internalCancel()
            else -> throw IllegalArgumentException("Unsupported result code: $resultCode")
        }
    }

    private fun internalSuccess(data: Intent?) {
        file?.let {
            val bmOptions = BitmapFactory.Options().apply { inJustDecodeBounds = false }
            // TODO
            onResult(BitmapFactory.decodeFile(it.absolutePath, bmOptions))
        } ?: onResult(null)
    }

    private fun internalCancel() {
        file?.delete()
        file = null
        onCancel()
    }

    companion object {

        fun externalIntentCameraDispatcher(
            activity: Activity,
            requestCode: Int,
            onResult: (Bitmap?) -> Unit,
            onCancel: () -> Unit
        ) = ExternalActivityCameraDispatcher(activity, onResult, onCancel) {
            activity.startActivityForResult(it, requestCode)
        }

        fun externalIntentCameraDispatcher(
            fragment: Fragment,
            requestCode: Int,
            onResult: (Bitmap?) -> Unit,
            onCancel: () -> Unit
        ) = ExternalActivityCameraDispatcher(fragment.requireContext(), onResult, onCancel) {
            fragment.startActivityForResult(it, requestCode)
        }
    }
}
