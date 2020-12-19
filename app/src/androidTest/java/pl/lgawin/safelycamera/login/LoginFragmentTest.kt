package pl.lgawin.safelycamera.login

import androidx.fragment.app.testing.launchFragmentInContainer
import org.junit.Test
import pl.lgawin.safelycamera.R
import java.util.concurrent.TimeUnit

class LoginFragmentTest {

    @Test
    fun name() {
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_SafelyCamera)

        TimeUnit.SECONDS.sleep(30)
    }
}
