package pl.lgawin.safelycamera.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import pl.lgawin.safelycamera.R

class LoginFragmentShould {

    @Test
    fun disableLoginButtonAtStart() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_SafelyCamera)
        onView(withText("Login")).check(matches(isDisplayed()))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun enableButtonWhenPasswordIsEntered() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_SafelyCamera)
        onView(withId(R.id.passwordInput)).perform(ViewActions.typeText("Password"))
        onView(withText("Login")).check(matches(isEnabled()))
    }
}
