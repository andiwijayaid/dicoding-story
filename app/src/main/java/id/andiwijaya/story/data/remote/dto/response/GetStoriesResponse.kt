package id.andiwijaya.story.data.remote.dto.response

data class GetStoriesResponse(
    val error: Boolean,
    val message: String,
    val listStory: ListStory
)

data class ListStory(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
)