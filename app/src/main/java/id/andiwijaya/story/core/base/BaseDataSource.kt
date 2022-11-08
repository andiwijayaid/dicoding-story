package id.andiwijaya.story.core.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.data.remote.dto.response.GenericResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>): Result<T> = try {
        val response = call()

        if (response.isSuccessful) response.body()?.let {
            Result.Success(it)
        } ?: handlerErrorBody(response)
        else handlerErrorBody(response)
    } catch (e: HttpException) {
        Result.Error(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        Result.Error("Server not found. Please check your internet connection")
    }

    private fun <T> handlerErrorBody(response: Response<T>): Result<T> {
        val type = object : TypeToken<GenericResponse>() {}.type
        return Result.Error(
            Gson().fromJson<GenericResponse?>(response.errorBody()?.charStream(), type).message,
            null,
            response.code()
        )
    }

}