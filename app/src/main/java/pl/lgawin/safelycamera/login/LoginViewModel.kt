package pl.lgawin.safelycamera.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import pl.lgawin.safelycamera.R

class LoginViewModel(private val authenticator: Authenticator) : ViewModel() {

    private val _errors = MutableLiveData<Int>()
    private val clearError = Observer<String> { _errors.value = null }

    val password = MutableLiveData("")
    val canProceed = password.map { it.isNotEmpty() }
    val errors: LiveData<Int> get() = _errors

    init {
        password.observeForever(clearError)
    }

    fun checkPassword(onSuccess: (token: String) -> Unit) {
        authenticator.checkPassword(password.value.orEmpty())
            ?.takeUnless { it.isBlank() }
            ?.let { token -> onSuccess(token) }
            ?: riseError()
    }

    private fun riseError() {
        _errors.value = R.string.incorrect_password_error
    }

    override fun onCleared() {
        super.onCleared()
        password.removeObserver(clearError)
    }
}
