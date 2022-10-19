package id.andiwijaya.story.data.util

import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status

object ConverterDataUtils {

    inline fun <T, R> Result<T>.mapToDomain(mapper: T.() -> R): Result<R> = when (status) {
        Status.LOADING -> Result.Loading()
        Status.ERROR -> Result.Error(message.orEmpty(), data?.mapper())
        Status.SUCCESS -> {
            data?.mapper()?.let { Result.Success(it) } ?: Result.Error(
                message.orEmpty(),
                data?.mapper()
            )
        }
    }

}