package id.andiwijaya.story.data.remote.api

import id.andiwijaya.story.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.data.remote.dto.response.GenericResponse
import id.andiwijaya.story.data.remote.dto.response.GetStoriesResponse
import id.andiwijaya.story.data.remote.dto.response.LoginResponse
import id.andiwijaya.story.data.remote.dto.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StoryApi {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int? = DEFAULT_PAGE_SIZE,
        @Query("location") location: Int? = ZERO
    ): Response<GetStoriesResponse>

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<GenericResponse>

}