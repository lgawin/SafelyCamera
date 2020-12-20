package pl.lgawin.safelycamera.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel(private val authenticator: Authenticator) : ViewModel() {

    val password = MutableLiveData("")
    val canProceed = password.map { it.isNotEmpty() }

    fun checkPassword(onSuccess: () -> Unit) {
        if (authenticator.checkPassword(password.value.orEmpty())) {
            onSuccess()
        }
    }
}
