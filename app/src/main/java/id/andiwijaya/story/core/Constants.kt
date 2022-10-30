package id.andiwijaya.story.core

object Constants {

    object Network {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }

    object Preference {
        const val SECRET_PREFERENCE = "encrypted-story-preference"
        const val PREF_KEY_TOKEN = "token"
    }

    object Argument {
        const val ARG_KEY_ID = "id"
    }

    object DateFormat {
        const val RESPONSE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DATE_TIME_SHORT_FORMAT = "HH.mm · dd/MM/yy"
    }

    const val ZERO = 0
    const val ONE = 1
    const val EMPTY_STRING = ""
    const val MIN_CHAR_ERROR_DELAY_IN_MILLIS = 1500L
    const val DEFAULT_PAGE_SIZE = 20

}