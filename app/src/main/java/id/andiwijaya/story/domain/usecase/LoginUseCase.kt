package id.andiwijaya.story.domain.usecase

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(request: LoginRequest): Flow<Result<LoginResult>> {
        return repository.login(request)
    }
}