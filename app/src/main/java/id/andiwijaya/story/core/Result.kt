package id.andiwijaya.story.core

sealed class Result<T>(val status: Status, val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Result<T>(Status.SUCCESS, data)
    class Error<T>(message: String, data: T? = null) : Result<T>(Status.ERROR, data, message)
    class Loading<T> : Result<T>(Status.LOADING, null)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}