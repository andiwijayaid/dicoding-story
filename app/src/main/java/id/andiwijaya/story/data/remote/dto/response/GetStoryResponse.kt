package id.andiwijaya.story.data.remote.dto.response

import id.andiwijaya.story.data.remote.dto.model.StoryDto
import id.andiwijaya.story.domain.model.Story

data class GetStoryResponse(
    val error: Boolean,
    val message: String,
    val story: StoryDto
)

fun GetStoryResponse.toStory() = with(story) {
    Story(id, name, description, photoUrl, createdAt, lat, lon)
}