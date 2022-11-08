package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.databinding.FragmentRegistrationBinding
import id.andiwijaya.story.presentation.viewmodel.RegistrationViewModel

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        stbRegistration.setNavigationOnClickListener { back() }
        edRegisterName.getEditText().addTextChangedListener {
            viewModel.name = it.toString()
            validateForm()
        }
        edRegisterEmail.getEditText().addTextChangedListener {
            viewModel.email = it.toString()
            validateForm()
        }
        edRegisterPassword.getEditText().addTextChangedListener {
            viewModel.password = it.toString()
            validateForm()
        }
        edRegisterConfirmPassword.getEditText().addTextChangedListener {
            viewModel.confirmationPassword = it.toString()
            validateForm()
        }
        btRegister.setOnClickListener {
            if (viewModel.isPasswordMatch()) {
                viewModel.register()
            } else {
                edRegisterConfirmPassword.setError(context?.getString(R.string.password_is_not_match))
                viewModel.isButtonEnable.postValue(false)
            }
        }
        viewModel.isButtonEnable.observe(viewLifecycleOwner) {
            btRegister.isEnabled(it)
        }
        observeRegisterResult()
    }

    private fun observeRegisterResult() = with(binding) {
        observeDataFlow(viewModel.registerResult, onLoad = {
            btRegister.isLoading(true)
        }, onError = {
            btRegister.isLoading(false)
            showErrorDialog(it.message)
        }) {
            btRegister.isLoading(false)
            showBottomDialog(
                context?.getString(R.string.registration_success).orEmpty(),
                context?.getString(R.string.registration_success_desc).orEmpty(),
                context?.getString(R.string.login).orEmpty(),
                R.drawable.ic_successful
            ) { back() }
        }
    }

    private fun validateForm() = with(viewModel) { isButtonEnable.postValue(isAllFilled()) }

}