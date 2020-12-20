package pl.lgawin.safelycamera

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import pl.lgawin.safelycamera.gallery.cameraRobot
import pl.lgawin.safelycamera.gallery.galleryRobot
import pl.lgawin.safelycamera.login.loginRobot

class SmokeTests {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun happyPath() {
        loginRobot {
            checkLoginDisabled()
            hideKeyboard()
            screenshot("1.Start")
            typePassword("p@ssword")
            hideKeyboard()
            screenshot("2.Ready to login")
            login()
        }
        galleryRobot {
            screenshot("3.Gallery - empty")
            startCamera()
        }
        cameraRobot {
            screenshot("4.Camera")
            takePhoto()
        }
        galleryRobot {
            screenshot("5.Gallery - one photo")
            startCamera()
        }
        cameraRobot {
            toggleCamera()
            takePhoto()
        }
        galleryRobot {
            screenshot("6.Gallery - two photos")
        }
    }
}
