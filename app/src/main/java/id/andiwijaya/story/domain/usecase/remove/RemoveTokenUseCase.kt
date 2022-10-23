package id.andiwijaya.story.domain.usecase.remove

import id.andiwijaya.story.domain.repository.StoryRepository
import javax.inject.Inject

class RemoveTokenUseCase @Inject constructor(private val repo: StoryRepository) {
    operator fun invoke() = repo.removeToken()
}