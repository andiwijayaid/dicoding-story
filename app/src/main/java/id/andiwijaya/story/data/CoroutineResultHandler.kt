package id.andiwijaya.story.data

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <A> resultFlow(
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit? = {}
): Flow<Result<A>> = flow {
    emit(Result.Loading())
    val response = networkCall()
    when (response.status) {
        Status.SUCCESS -> {
            response.data?.let {
                saveCallResult(it)
                emit(Result.Success(response.data))
            } ?: emit(Result.Error(response.message.orEmpty(), null, response.code))
        }
        Status.ERROR -> {
            emit(Result.Error(response.message.orEmpty(), null, response.code))
        }
        Status.LOADING -> {}
    }
}