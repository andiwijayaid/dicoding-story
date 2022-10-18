package id.andiwijaya.story.data.remote.dto.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)