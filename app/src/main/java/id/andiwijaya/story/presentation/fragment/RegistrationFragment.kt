package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.databinding.FragmentRegistrationBinding
import id.andiwijaya.story.presentation.viewmodel.RegistrationViewModel

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        etName.getEditText().addTextChangedListener {
            viewModel.name = it.toString()
            validateForm()
        }
        etEmail.getEditText().addTextChangedListener {
            viewModel.email = it.toString()
            validateForm()
        }
        etPassword.getEditText().addTextChangedListener {
            viewModel.password = it.toString()
            validateForm()
        }
        etConfirmPassword.getEditText().addTextChangedListener {
            viewModel.confirmationPassword = it.toString()
            validateForm()
        }
        btRegister.setOnClickListener {
            if (viewModel.isPasswordMatch()) {
                viewModel.register()
            } else {
                etConfirmPassword.setError(context?.getString(R.string.password_is_not_match))
                viewModel.isButtonEnable.postValue(false)
            }
        }
        viewModel.isButtonEnable.observe(viewLifecycleOwner) {
            btRegister.isEnabled(it)
        }
    }

    private fun validateForm() = with(viewModel) { isButtonEnable.postValue(isAllFilled()) }

}