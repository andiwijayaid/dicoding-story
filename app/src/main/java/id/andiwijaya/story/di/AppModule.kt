package id.andiwijaya.story.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.story.core.Constants.Network.BASE_URL
import id.andiwijaya.story.data.remote.CommonHeaderInterceptor
import id.andiwijaya.story.data.remote.StoryApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

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
    fun provideStoryApiService(): StoryApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StoryApi::class.java)

}