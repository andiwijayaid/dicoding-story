package id.andiwijaya.story.data.remote.dto.response

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)