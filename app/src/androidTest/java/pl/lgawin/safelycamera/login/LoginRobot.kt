package pl.lgawin.safelycamera.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.testing.FragmentTest
import pl.lgawin.safelycamera.testing.TestRobot

internal class LoginRobot : TestRobot() {

    private val loginButton get() = onView(withText("Login"))
    private val passwordInput get() = onView(withId(R.id.passwordInput))

    // checks
    fun checkLoginDisabled() {
        loginButton.check(matches(allOf(isDisplayed(), not(isEnabled()))))
    }

    fun checkLoginEnabled() {
        loginButton.check(matches(isEnabled()))
    }

    // actions
    fun typePassword(password: String) {
        passwordInput.perform(typeText(password))
    }

    fun login() {
        loginButton.perform(click())
    }

    fun hideKeyboard() {
        Espresso.closeSoftKeyboard()
    }
}

@Suppress("unused")
internal fun FragmentTest.loginRobot(
    authenticator: Authenticator = mockk(relaxed = true),
    navController: TestNavHostController? = null,
    function: LoginRobot.() -> Unit
): LoginRobot {
    launchFragmentInContainer(themeResId = R.style.Theme_SafelyCamera) { LoginFragment(authenticator) }
        .onFragment { fragment ->
            navController?.let { Navigation.setViewNavController(fragment.requireView(), it) }
        }
    return loginRobot(function)
}

internal fun loginRobot(function: LoginRobot.() -> Unit) = LoginRobot().apply(function)
