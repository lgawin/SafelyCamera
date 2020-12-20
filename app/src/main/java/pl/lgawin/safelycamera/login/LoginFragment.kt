package pl.lgawin.safelycamera.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.databinding.LoginFragmentBinding
import pl.lgawin.safelycamera.security.TokenHolder
import pl.lgawin.safelycamera.utils.simpleFactory

class LoginFragment(
    private val authenticator: Authenticator,
    private val tokenHolder: TokenHolder
) : Fragment(), LoginHandler {

    private val viewModel: LoginViewModel by viewModels { simpleFactory { LoginViewModel(authenticator) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        LoginFragmentBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
                handler = this@LoginFragment
                lifecycleOwner = viewLifecycleOwner
            }
            .root

    override fun onLogin() {
        viewModel.checkPassword(onSuccess = { token ->
            tokenHolder.token = token
            findNavController().navigate(R.id.action_loginFragment_to_galleryFragment)
        })
    }
}

interface LoginHandler {
    fun onLogin()
}
