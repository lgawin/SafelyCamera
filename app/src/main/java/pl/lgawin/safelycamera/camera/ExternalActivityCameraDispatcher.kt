package pl.lgawin.safelycamera.camera

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import pl.lgawin.safelycamera.BuildConfig
import pl.lgawin.safelycamera.storage.PhotosStorage
import java.io.File

class ExternalActivityCameraDispatcher(
    private val context: Context,
    private val onResult: (Bitmap?) -> Unit,
    private val onCancel: () -> Unit,
    private val startActivityForResult: (Intent) -> Unit
) : CameraDispatcher {

    private var file: File? = null
    private val storage = PhotosStorage(context)

    override fun dispatchTakePicture(): File? {
        file = storage.createTempFile()
        file?.let {
            val photoUri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", it)
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
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

    @Suppress("UNUSED_PARAMETER")
    private fun internalSuccess(data: Intent?) {
        file?.let {
            // TODO (change to coil or add scaling?, or just return nothing)
            val bmOptions = BitmapFactory.Options().apply { inJustDecodeBounds = false }
            val bitmap = BitmapFactory.decodeFile(it.absolutePath, bmOptions)
            storage.moveToSecure(it)
            file = null
            onResult(bitmap)
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

