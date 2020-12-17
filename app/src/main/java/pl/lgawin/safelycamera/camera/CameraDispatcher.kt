package pl.lgawin.safelycamera.camera

import android.content.Intent
import java.io.File

interface CameraDispatcher {
    fun dispatchTakePicture(): File?
    fun dispatchResult(resultCode: Int, data: Intent?)
}
