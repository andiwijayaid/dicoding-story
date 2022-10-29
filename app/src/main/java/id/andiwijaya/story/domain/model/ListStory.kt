package id.andiwijaya.story.domain.model

data class ListStory(
    val error: Boolean,
    val message: String,
    val stories: List<Story>
)