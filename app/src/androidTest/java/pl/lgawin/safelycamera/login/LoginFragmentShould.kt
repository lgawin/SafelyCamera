package pl.lgawin.safelycamera.login

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import pl.lgawin.safelycamera.testing.FragmentTest
import pl.lgawin.safelycamera.R

class LoginFragmentShould: FragmentTest {

    @Test
    fun disableLoginButtonAtStart() {
        loginRobot {
            checkLoginDisabled()
        }
    }

    @Test
    fun enableButtonWhenPasswordIsEntered() {
        loginRobot {
            typePassword("Password")
            hideKeyboard()
            checkLoginEnabled()
        }
    }

    @Test
    fun navigateToGalleryAfterClickingLogin() {
        val navController = testNavController(R.navigation.nav_graph)
        loginRobot(navController) {
            typePassword("OpenUp")
            hideKeyboard()
            login()
        }
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.galleryFragment)
    }
}
