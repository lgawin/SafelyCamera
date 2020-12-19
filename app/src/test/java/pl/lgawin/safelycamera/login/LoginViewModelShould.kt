package pl.lgawin.safelycamera.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import pl.lgawin.safelycamera.testing.getOrAwaitValue

internal class LoginViewModelShould {

    val viewModel = LoginViewModel()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `not allow to proceed at startup`() {
        // then
        val canProceed = viewModel.canProceed.getOrAwaitValue()
        assertThat(canProceed).isFalse()
    }

    @Test
    fun `allow to proceed after putting password`() {
        // when
        viewModel.password.value = "Password"
        // then
        val canProceed = viewModel.canProceed.getOrAwaitValue()
        assertThat(canProceed).isTrue()
    }

    @Test
    fun `not allow to proceed after deleting password`() {
        viewModel.password.value = "Password"
        // when
        viewModel.password.value = ""
        // then
        val canProceed = viewModel.canProceed.getOrAwaitValue()
        assertThat(canProceed).isFalse()
    }
}
