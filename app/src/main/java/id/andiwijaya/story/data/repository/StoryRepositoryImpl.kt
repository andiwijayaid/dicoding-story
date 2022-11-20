package id.andiwijaya.story.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.andiwijaya.story.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.StoryDatabase
import id.andiwijaya.story.data.local.StoryLocalDataSource
import id.andiwijaya.story.data.mediator.StoryRemoteMediator
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.request.RegisterRequest
import id.andiwijaya.story.data.remote.dto.response.toGenericResult
import id.andiwijaya.story.data.remote.dto.response.toLoginResult
import id.andiwijaya.story.data.remote.dto.response.toRegisterResult
import id.andiwijaya.story.data.remote.service.StoryRemoteDataSource
import id.andiwijaya.story.data.resultFlow
import id.andiwijaya.story.data.util.ConverterDataUtils.mapToDomain
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.model.RegisterResult
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepositoryImpl @Inject constructor(
    private val storyDatabase: StoryDatabase,
    private val remoteDataSource: StoryRemoteDataSource,
    private val localDataSource: StoryLocalDataSource
) : StoryRepository {

    override fun login(request: LoginRequest): Flow<Result<LoginResult>> = resultFlow(
        networkCall = { remoteDataSource.login(request).mapToDomain { toLoginResult() } },
        saveCallResult = { localDataSource.saveToken(it.token) }
    )

    override fun register(request: RegisterRequest): Flow<Result<RegisterResult>> = resultFlow(
        networkCall = { remoteDataSource.register(request).mapToDomain { toRegisterResult() } }
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun getStories() = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
        remoteMediator = StoryRemoteMediator(storyDatabase, remoteDataSource),
        pagingSourceFactory = { storyDatabase.storyDao().getAllStory() }
    ).flow

    override fun getStoriesWithLocation(): LiveData<List<Story>> {
        return storyDatabase.storyDao().getStoriesWithLocation()
    }

    override fun postStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ) = resultFlow(
        networkCall = {
            remoteDataSource.postStory(
                photo, description, lat, lon
            ).mapToDomain { toGenericResult() }
        }
    )

    override fun loadToken(): String = localDataSource.loadToken()

    override fun removeToken() {
        localDataSource.removeToken()
    }

}