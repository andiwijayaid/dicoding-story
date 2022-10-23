package id.andiwijaya.story.data.remote.dto.response

import id.andiwijaya.story.domain.model.RegisterResult

data class RegisterResponse(
    val error: Boolean,
    val message: String
)

fun RegisterResponse.toRegisterResult() = RegisterResult(error, message)