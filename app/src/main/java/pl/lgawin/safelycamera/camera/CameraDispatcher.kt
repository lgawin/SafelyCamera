package pl.lgawin.safelycamera.camera

import android.content.Intent

interface CameraDispatcher {
    fun dispatchTakePicture()
    fun dispatchResult(resultCode: Int, data: Intent?)
}
