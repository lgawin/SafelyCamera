package pl.lgawin.safelycamera.gallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.testing.TestRobot

internal fun galleryRobot(function: GalleryRobot.() -> Unit) = GalleryRobot().apply(function)

internal class GalleryRobot : TestRobot() {

    private val cameraButton get() = onView(withId(R.id.floating_action_button))

    // checks
    // actions
    fun startCamera() {
        cameraButton.perform(click())
        device.waitForIdle()
    }
}
