package id.andiwijaya.story.data.remote.dto.response

import id.andiwijaya.story.data.remote.dto.model.StoryDto
import id.andiwijaya.story.domain.model.ListStory
import id.andiwijaya.story.domain.model.Story

data class GetStoriesResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryDto>
)

fun GetStoriesResponse.toListStory() = ListStory(
    error = error, message = message, stories = listStory.map {
        Story(it.id, it.name, it.description, it.photoUrl, it.createdAt, it.lat, it.lon)
    }
)