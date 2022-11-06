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
        const val FILE_NAME = "'story'-yyyy-MM-dd HH.mm.ss"
    }

    const val ZERO = 0
    const val ZERO_LONG = 0L
    const val ONE = 1
    const val FIVE = 5
    const val EMPTY_STRING = ""
    const val MIN_CHAR_ERROR_DELAY_IN_MILLIS = 1500L
    const val DEFAULT_PAGE_SIZE = 20
    const val INITIAL_COMPRESS_QUALITY = 100
    const val MAX_STREAM_LENGTH = 1000000
    const val IMAGE_JPEG = "image/jpeg"
    const val DOT_JPG = ".jpg"
    const val BYTE_MULTIPLIER = 1024
    const val MULTIPART_BODY_NAME = "photo"
    const val ONE_FLOAT = 1f
    const val PHOTO_ANIM_DURATION = 500L
    const val SECONDS_IN_MINUTE = 60
    const val MINUTES_IN_HOUR = 60
    const val HOURS_IN_A_DAY = 24

}