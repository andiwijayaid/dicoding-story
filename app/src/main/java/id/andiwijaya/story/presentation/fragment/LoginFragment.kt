package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.databinding.FragmentLoginBinding
import id.andiwijaya.story.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        btLogin.setOnClickListener {
            viewModel.login()
        }
        tvRegister.setOnClickListener {
            goTo(LoginFragmentDirections.actionLoginToRegistration())
        }
        edLoginEmail.getEditText().addTextChangedListener {
            viewModel.email = it.toString()
            validateForm()
        }
        edLoginPassword.getEditText().addTextChangedListener {
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
                401 -> edLoginPassword.setError(context?.getString(R.string.wrong_email_or_password))
                else -> showErrorDialog(it.message)
            }
        }) {
            btLogin.isLoading(false)
            pop(LoginFragmentDirections.actionLoginToHome())
        }
    }
}