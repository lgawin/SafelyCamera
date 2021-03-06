package pl.lgawin.safelycamera.login

import com.google.common.truth.Truth.assertThat
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.security.TokenHolder
import pl.lgawin.safelycamera.testing.FragmentTest

class LoginFragmentShould : FragmentTest {

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
    fun validateEnteredPassword() {
        val authenticator = mockk<Authenticator>(relaxed = true)
        val passwordSlot = slot<String>()
        loginRobot(authenticator) {
            typePassword("password")
            hideKeyboard()
            login()
        }
        verify { authenticator.checkPassword(capture(passwordSlot)) }
        assertThat(passwordSlot.captured).isEqualTo("password")
    }

    @Test
    fun switchToGalleryAfterSuccessfulPasswordValidation() {
        val authenticator = mockk<Authenticator>(relaxed = true) {
            every { checkPassword(any()) } returns "security token"
        }
        val navController = testNavController(R.navigation.nav_graph)
        loginRobot(authenticator, navController) {
            typePassword("OpenUp")
            hideKeyboard()
            login()
        }
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.galleryFragment)
    }

    @Test
    fun storesSecureTokenAfterSuccessfulPasswordValidation() {
        val authenticator = mockk<Authenticator>(relaxed = true) {
            every { checkPassword(any()) } returns "security token"
        }
        val navController = testNavController(R.navigation.nav_graph)
        val tokenHolder = object: TokenHolder {
            override var token: String? = null
        }
        loginRobot(authenticator, navController, tokenHolder) {
            typePassword("OpenUp")
            hideKeyboard()
            login()
        }
        assertThat(tokenHolder.token).isEqualTo("security token")
    }

    @Test
    fun stayOnLoginScreenWhenPasswordInvalid() {
        val authenticator = mockk<Authenticator>(relaxed = true)
        val navController = spyk(testNavController(R.navigation.nav_graph))
        loginRobot(authenticator, navController) {
            typePassword("OpenUp")
            hideKeyboard()
            login()
        }
        verify { navController wasNot Called }
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.loginFragment)
    }

    @Test
    fun showErrorForInvalidPasswordAndClearItWhenPasswordChanges() {
        val authenticator = mockk<Authenticator>(relaxed = true)
        val navController = spyk(testNavController(R.navigation.nav_graph))
        loginRobot(authenticator, navController) {
            typePassword("OpenUp")
            hideKeyboard()
            login()
            checkErrorShown("Incorrect password")
            typePassword("Now")
            checkNoError()
        }
    }
}
