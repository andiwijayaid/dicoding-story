package id.andiwijaya.story.core

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>): Result<T> = try {
        val response = call()

        if (response.isSuccessful) response.body()?.let {
            Result.Success(it)
        } ?: Result.Error("")
        else Result.Error("")
    } catch (e: HttpException) {
        Result.Error(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        Result.Error("Server not found. Please check your internet connection")
    }

}