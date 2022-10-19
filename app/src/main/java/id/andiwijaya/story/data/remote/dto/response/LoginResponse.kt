package id.andiwijaya.story.data.remote.dto.response

import id.andiwijaya.story.data.remote.dto.model.LoginResultDto
import id.andiwijaya.story.domain.model.LoginResult

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResultDto
)

fun LoginResponse.toLoginResult() = LoginResult(
    error, message, loginResult.userId, loginResult.name, loginResult.token
)