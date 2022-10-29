package id.andiwijaya.story.data.remote.service

import id.andiwijaya.story.core.BaseDataSource
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.api.StoryApi
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.data.remote.dto.response.GetStoriesResponse
import id.andiwijaya.story.data.remote.dto.response.LoginResponse
import id.andiwijaya.story.data.remote.dto.response.RegisterResponse
import javax.inject.Inject

class StoryRemoteDataSource @Inject constructor(
    private val api: StoryApi
) : BaseDataSource() {

    suspend fun login(request: LoginRequest): Result<LoginResponse> = getResultWithSingleObject {
        api.login(request)
    }

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return getResultWithSingleObject { api.register(request) }
    }

    suspend fun getStories(
        page: Int, size: Int, location: Int? = null
    ): Result<GetStoriesResponse> {
        return getResultWithSingleObject { api.getStories(page, size, location) }
    }

}