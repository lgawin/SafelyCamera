package pl.lgawin.safelycamera.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel : ViewModel() {

    val password = MutableLiveData("")
    val canProceed = password.map { it.isNotEmpty() }
}
