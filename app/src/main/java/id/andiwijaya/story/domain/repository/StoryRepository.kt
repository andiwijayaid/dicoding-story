package id.andiwijaya.story.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.domain.model.GenericResult
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.model.RegisterResult
import id.andiwijaya.story.domain.model.Story
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResult>>
    fun register(request: RegisterRequest): Flow<Result<RegisterResult>>
    fun getStories(): Flow<PagingData<Story>>
    fun getStoriesWithLocation(): LiveData<List<Story>>
    fun postStory(
        photo: MultipartBody.Part, description: RequestBody, lat: RequestBody?, lon: RequestBody?
    ): Flow<Result<GenericResult>>
    fun loadToken(): String
    fun removeToken()
}