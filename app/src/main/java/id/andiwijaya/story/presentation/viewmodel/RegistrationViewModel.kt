package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.BaseViewModel
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.model.RegisterResult
import id.andiwijaya.story.domain.usecase.post.RegisterUseCase
import id.andiwijaya.story.presentation.fragment.RegistrationFragmentDirections
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterResult>>()
    val registerResult: LiveData<Result<RegisterResult>> = _registerResult

    val isButtonEnable = MutableLiveData(false)
    var name = EMPTY_STRING
    var email = EMPTY_STRING
    var password = EMPTY_STRING
    var confirmationPassword = EMPTY_STRING

    fun isAllFilled() = listOf(
        name, email, password, confirmationPassword
    ).contains(EMPTY_STRING).not()

    fun isPasswordMatch() = password == confirmationPassword

    fun register() = collectFlow(
        registerUseCase(RegisterRequest(name, email, password)), _registerResult
    )

    fun navigateToLogin() = goTo(RegistrationFragmentDirections.actionRegistrationToLogin())

}