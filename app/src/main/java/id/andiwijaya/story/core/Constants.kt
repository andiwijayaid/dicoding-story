package id.andiwijaya.story.core

object Constants {

    object Network {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
        const val AUTHORIZATION = "AUTHORIZATION"
        const val BEARER = "Bearer"
    }

    object Preference {
        const val SECRET_PREFERENCE = "encrypted-story-preference"
        const val PREF_KEY_TOKEN = "token"
    }

    const val ZERO = 0
    const val EMPTY_STRING = ""
    const val MIN_CHAR_ERROR_DELAY_IN_MILLIS = 1500L

}