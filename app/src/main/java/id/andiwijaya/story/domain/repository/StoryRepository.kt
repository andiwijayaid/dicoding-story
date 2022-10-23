package id.andiwijaya.story.domain.repository

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.domain.model.LoginResult
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResult>>
    fun loadToken(): String
    fun removeToken()
}