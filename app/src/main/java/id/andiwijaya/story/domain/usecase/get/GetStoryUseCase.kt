package id.andiwijaya.story.domain.usecase.get

import id.andiwijaya.story.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoryUseCase @Inject constructor(private val repository: StoryRepository) {
    operator fun invoke(id: String) = repository.getStory(id)
}