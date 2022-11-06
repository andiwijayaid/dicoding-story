package id.andiwijaya.story.domain.usecase.post

import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.repository.StoryRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: StoryRepository) {
    operator fun invoke(request: RegisterRequest) = repository.register(request)
}