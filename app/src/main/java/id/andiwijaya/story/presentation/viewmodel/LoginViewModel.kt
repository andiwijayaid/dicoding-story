package id.andiwijaya.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.base.BaseViewModel
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.usecase.post.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResult>>()
    val loginResult: LiveData<Result<LoginResult>> = _loginResult

    val isButtonEnable = MutableLiveData(false)
    var email = EMPTY_STRING
    var password = EMPTY_STRING

    fun isAllFilled() = listOf(email, password).contains(EMPTY_STRING).not()

    fun login() = collectFlow(loginUseCase(LoginRequest(email, password)), _loginResult)

}