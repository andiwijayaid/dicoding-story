package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.databinding.FragmentLoginBinding
import id.andiwijaya.story.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        btLogin.setOnClickListener {
            viewModel.login(etEmail.text, etPassword.text)
        }

        observeDataFlow(viewModel.loginResult, onLoad = {
            Log.d("ASDCV", "Loading")
        }, onError = {
            Log.d("ASDCV", "Error")
        }) {
            Log.d("ASDCV", "Success $it")
        }
    }
}