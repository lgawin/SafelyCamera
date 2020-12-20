package pl.lgawin.safelycamera.testing

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import java.io.File

interface UiAutomatorRobot : BaseTest {

    val device
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())!!

    fun screenshot(name: String) {
        val path = File(context.getExternalFilesDir("Screenshots"), "$name.png")
        println("Save screenshot: $path")
        device.takeScreenshot(path)
    }
}
