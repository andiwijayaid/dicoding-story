package id.andiwijaya.story.domain.model

data class LoginResult(
    val error: Boolean,
    val message: String,
    val userId: String,
    val name: String,
    val token: String
)