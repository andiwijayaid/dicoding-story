package id.andiwijaya.story.data.remote.service

import id.andiwijaya.story.core.BaseDataSource
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.api.StoryApi
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.response.LoginResponse
import javax.inject.Inject

class StoryRemoteDataSource @Inject constructor(
    private val api: StoryApi
) : BaseDataSource() {

    suspend fun login(request: LoginRequest): Result<LoginResponse> = getResultWithSingleObject {
        api.login(request)
    }

}