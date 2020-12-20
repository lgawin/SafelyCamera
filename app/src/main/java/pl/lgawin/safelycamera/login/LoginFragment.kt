package pl.lgawin.safelycamera.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import pl.lgawin.safelycamera.R
import pl.lgawin.safelycamera.databinding.LoginFragmentBinding
import pl.lgawin.safelycamera.utils.simpleFactory

class LoginFragment(private val authenticator: Authenticator) : Fragment() {

    private val viewModel by viewModels<LoginViewModel> {
        simpleFactory { LoginViewModel(authenticator) }
    }

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        LoginFragmentBinding.inflate(inflater, container, false)
            .also { binding = it}
            .apply {
                vm = viewModel
                lifecycleOwner = viewLifecycleOwner
                loginButton.setOnClickListener {
                    viewModel.checkPassword(onSuccess = {
                        findNavController().navigate(R.id.action_loginFragment_to_galleryFragment)
                    })
                }
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errors.observe(viewLifecycleOwner, Observer { errorRes ->
            binding.loginError = errorRes?.let { getString(it) }
        })
    }
}
