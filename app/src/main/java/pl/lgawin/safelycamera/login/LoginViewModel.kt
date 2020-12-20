package pl.lgawin.safelycamera.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import pl.lgawin.safelycamera.R

class LoginViewModel(private val authenticator: Authenticator) : ViewModel() {

    val password = MutableLiveData("")
    val canProceed = password.map { it.isNotEmpty() }
    private val _errors = MutableLiveData<Int>()
    val errors: LiveData<Int> get() = _errors

    private val clearError = Observer<String> { _errors.value = null }

    init {
        password.observeForever(clearError)
    }

    fun checkPassword(onSuccess: () -> Unit) {
        if (authenticator.checkPassword(password.value.orEmpty())) {
            onSuccess()
        } else {
            _errors.value = R.string.incorrect_password_error
        }
    }

    override fun onCleared() {
        super.onCleared()
        password.removeObserver(clearError)
    }
}
