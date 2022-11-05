package id.andiwijaya.story.data.remote.dto.response

import id.andiwijaya.story.domain.model.GenericResult

data class GenericResponse(
    val error: Boolean,
    val message: String
)

fun GenericResponse.toGenericResult() = GenericResult(error, message)