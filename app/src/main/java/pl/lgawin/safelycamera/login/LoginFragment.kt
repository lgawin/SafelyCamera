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

class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LoginFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                loginButton.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_galleryFragment) }
            }
            .root
    }

}
