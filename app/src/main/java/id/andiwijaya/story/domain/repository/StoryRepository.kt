package id.andiwijaya.story.domain.repository

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.model.RegisterResult
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResult>>
    fun register(request: RegisterRequest): Flow<Result<RegisterResult>>
    fun loadToken(): String
    fun removeToken()
}