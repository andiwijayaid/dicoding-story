package id.andiwijaya.story.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.story.core.Constants.Network.BASE_URL
import id.andiwijaya.story.core.SecurePrefManager
import id.andiwijaya.story.core.StoryDatabase
import id.andiwijaya.story.data.local.StoryLocalDataSource
import id.andiwijaya.story.data.remote.CommonHeaderInterceptor
import id.andiwijaya.story.data.remote.api.StoryApi
import id.andiwijaya.story.data.remote.service.StoryRemoteDataSource
import id.andiwijaya.story.data.repository.StoryRepositoryImpl
import id.andiwijaya.story.domain.repository.StoryRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSecurePrefManager(application: Application) = SecurePrefManager(application)

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideCommonHeaderInterceptor(securePrefManager: SecurePrefManager) =
        CommonHeaderInterceptor(securePrefManager)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        commonHeader: CommonHeaderInterceptor
    ): OkHttpClient {
        return okHttpClientBuilder
            .addInterceptor(commonHeader)
            .build()
    }

    @Singleton
    @Provides
    fun provideStoryApiService(
        okHttpClient: OkHttpClient
    ): StoryApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(StoryApi::class.java)

    @Singleton
    @Provides
    fun provideStoryDatabase(application: Application) = StoryDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideStoryLocalDataSource(securePrefManager: SecurePrefManager) =
        StoryLocalDataSource(securePrefManager)

    @Singleton
    @Provides
    fun provideStoryRemoteDataSource(api: StoryApi) = StoryRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyDatabase: StoryDatabase,
        remoteDataSource: StoryRemoteDataSource,
        localDataSource: StoryLocalDataSource
    ): StoryRepository {
        return StoryRepositoryImpl(storyDatabase, remoteDataSource, localDataSource)
    }

}