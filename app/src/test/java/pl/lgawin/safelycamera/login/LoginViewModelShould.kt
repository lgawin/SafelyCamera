package pl.lgawin.safelycamera.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.testing.getOrAwaitValue

internal class LoginViewModelShould {

    @MockK(relaxed = true)
    lateinit var authenticator: Authenticator

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(authenticator)
    }

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
        // given
        viewModel.password.value = "Password"
        // when
        viewModel.password.value = ""
        // then
        val canProceed = viewModel.canProceed.getOrAwaitValue()
        assertThat(canProceed).isFalse()
    }

    @Test
    fun `emit error for incorrect password`() {
        // given
        every { authenticator.checkPassword(any()) } returns false
        // when
        viewModel.checkPassword {  }
        val error = viewModel.errors.getOrAwaitValue()
        // then
        assertThat(error).isEqualTo(R.string.incorrect_password_error)
    }
}
