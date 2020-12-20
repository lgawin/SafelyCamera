package pl.lgawin.safelycamera.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.lgawin.safelycamera.databinding.LoginFragmentBinding
import pl.lgawin.safelycamera.serviceLocator
import pl.lgawin.safelycamera.utils.simpleFactory

class LoginFragment(private val authenticator: Authenticator) : Fragment() {

    private val viewModel: LoginViewModel by viewModels { simpleFactory { LoginViewModel(authenticator) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        LoginFragmentBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
                lifecycleOwner = viewLifecycleOwner
                loginButton.setOnClickListener {
                    viewModel.checkPassword(onSuccess = { token ->
                        requireContext().serviceLocator.token = token
                        val direction = LoginFragmentDirections.actionLoginFragmentToGalleryFragment(token)
                        findNavController().navigate(direction)
                    })
                }
            }
            .root
}
