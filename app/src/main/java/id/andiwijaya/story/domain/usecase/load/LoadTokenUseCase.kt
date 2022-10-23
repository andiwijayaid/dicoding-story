package id.andiwijaya.story.domain.usecase.load

import id.andiwijaya.story.domain.repository.StoryRepository
import javax.inject.Inject

class LoadTokenUseCase @Inject constructor(private val repo: StoryRepository) {
    operator fun invoke() = repo.loadToken()
}