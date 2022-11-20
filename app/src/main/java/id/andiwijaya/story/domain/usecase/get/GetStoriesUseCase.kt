package id.andiwijaya.story.domain.usecase.get

import id.andiwijaya.story.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val repository: StoryRepository) {
    operator fun invoke() = repository.getStories()

    fun invokeCache() = repository.getStoriesWithLocation()
}