package id.andiwijaya.story.data.repository

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.request.LoginRequest
import id.andiwijaya.story.data.remote.dto.response.toLoginResult
import id.andiwijaya.story.data.remote.service.StoryRemoteDataSource
import id.andiwijaya.story.data.resultFlow
import id.andiwijaya.story.data.util.ConverterDataUtils.mapToDomain
import id.andiwijaya.story.domain.model.LoginResult
import id.andiwijaya.story.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: StoryRemoteDataSource
) : LoginRepository {

    override fun login(request: LoginRequest): Flow<Result<LoginResult>> = resultFlow(
        networkCall = {
            remoteDataSource.login(request).mapToDomain { toLoginResult() }
        },
        saveCallResult = {

        }
    )

}