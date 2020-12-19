package pl.lgawin.safelycamera.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.common.truth.Truth.assertThat
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
        onView(withId(R.id.passwordInput)).perform(typeText("Password"))
        Espresso.closeSoftKeyboard()
        onView(withText("Login")).check(matches(isEnabled()))
    }

    @Test
    fun navigateToGalleryAfterClickingLogin() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            setGraph(R.navigation.nav_graph)
        }
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_SafelyCamera).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.passwordInput)).perform(typeText("Password"))
        Espresso.closeSoftKeyboard()
        onView(withText("Login")).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.galleryFragment)
    }
}
