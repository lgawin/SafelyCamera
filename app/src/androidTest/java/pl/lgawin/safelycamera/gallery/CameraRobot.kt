package pl.lgawin.safelycamera.gallery

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import pl.lgawin.safelycamera.testing.UiAutomatorRobot

internal fun cameraRobot(function: CameraRobot.() -> Unit) = CameraRobot().apply(function)

internal class CameraRobot : UiAutomatorRobot {

    val defaultTimeout: Long get() = 1000

    private val shutterButton = By.res("com.android.camera2:id/shutter_button")
    private val doneButton = By.res("com.android.camera2:id/done_button")
    private val cameraToggleButton = By.res("com.android.camera2:id/camera_toggle_button")
    private val moreButton = By.res("com.android.camera2:id/three_dots")

    fun takePhoto() {
        device.findObject(shutterButton).clickAndWait(Until.newWindow(), defaultTimeout)
        device.findObject(doneButton).clickAndWait(Until.newWindow(), defaultTimeout)
        device.waitForIdle()
    }

    fun toggleCamera() {
        device.findObject(moreButton).click()
        device.wait(Until.hasObject(cameraToggleButton), defaultTimeout)
        device.findObject(cameraToggleButton).click()
        device.waitForIdle()
    }
}
