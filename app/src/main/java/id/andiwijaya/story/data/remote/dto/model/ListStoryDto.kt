package id.andiwijaya.story.data.remote.dto.model

data class ListStoryDto(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
)