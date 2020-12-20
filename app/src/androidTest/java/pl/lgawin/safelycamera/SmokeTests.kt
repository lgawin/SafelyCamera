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
            typePassword("p@ss")
            hideKeyboard()
            login()
            screenshot("2.Incorrect password")
            typePassword("word")
            hideKeyboard()
            screenshot("3.Ready to login")
            login()
        }
        galleryRobot {
            screenshot("4.Gallery - empty")
            startCamera()
        }
        cameraRobot {
            screenshot("5.Camera")
            takePhoto()
        }
        galleryRobot {
            screenshot("6.Gallery - one photo")
            startCamera()
        }
        cameraRobot {
            toggleCamera()
            takePhoto()
        }
        galleryRobot {
            screenshot("7.Gallery - two photos")
        }
    }
}
