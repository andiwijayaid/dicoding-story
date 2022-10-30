package id.andiwijaya.story.domain.repository

import androidx.paging.PagingData
import id.andiwijaya.story.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.model.RegisterResult
import id.andiwijaya.story.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResult>>
    fun register(request: RegisterRequest): Flow<Result<RegisterResult>>
    fun getStories(
        page: Int,
        size: Int? = DEFAULT_PAGE_SIZE,
        location: Int? = null
    ): Flow<PagingData<Story>>
    fun getStory(id: String): Flow<Result<Story>>
    fun loadToken(): String
    fun removeToken()
}