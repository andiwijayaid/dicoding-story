package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
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
            viewModel.login()
        }
        tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToRegistration())
        }
        etEmail.getEditText().addTextChangedListener {
            viewModel.email = it.toString()
            validateForm()
        }
        etPassword.getEditText().addTextChangedListener {
            viewModel.password = it.toString()
            validateForm()
        }
        observeLogin()
        viewModel.isButtonEnable.observe(viewLifecycleOwner) {
            btLogin.isEnabled(it)
        }
    }

    private fun validateForm() = with(viewModel) { isButtonEnable.postValue(isAllFilled()) }

    private fun observeLogin() = with(binding) {
        observeDataFlow(viewModel.loginResult, onLoad = {
            btLogin.isLoading(true)
        }, onError = {
            btLogin.isLoading(false)
            when (it.code) {
                401 -> etPassword.setError(context?.getString(R.string.wrong_email_or_password))
                else -> showErrorDialog(it.message)
            }
        }) {
            btLogin.isLoading(false)
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
        }
    }
}