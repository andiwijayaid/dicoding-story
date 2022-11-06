package id.andiwijaya.story.core.util

import android.content.Context
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.DateFormat.DATE_TIME_SHORT_FORMAT
import id.andiwijaya.story.core.Constants.DateFormat.RESPONSE_DATE_FORMAT
import id.andiwijaya.story.core.Constants.HOURS_IN_A_DAY
import id.andiwijaya.story.core.Constants.MINUTES_IN_HOUR
import id.andiwijaya.story.core.Constants.SECONDS_IN_MINUTE
import id.andiwijaya.story.core.Constants.ZERO_LONG
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtil {

    fun convertDateAndTime(dateTime: String): String = LocalDateTime.parse(
        dateTime, DateTimeFormatter.ofPattern(RESPONSE_DATE_FORMAT, Locale.ENGLISH)
    ).format(DateTimeFormatter.ofPattern(DATE_TIME_SHORT_FORMAT))

    fun timeDifference(dateTime: String, context: Context): String {
        val date = SimpleDateFormat(RESPONSE_DATE_FORMAT, Locale.ENGLISH).parse(dateTime)
        val millis: Long = date?.time ?: ZERO_LONG
        val diffMillis = Date().time - millis

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        return if (seconds < SECONDS_IN_MINUTE) {
            context.getString(R.string.second_format, seconds)
        } else if (minutes < MINUTES_IN_HOUR) {
            context.getString(R.string.minute_format, minutes)
        } else if (hours < HOURS_IN_A_DAY) {
            context.getString(R.string.hour_format, hours)
        } else {
            convertDateAndTime(dateTime)
        }
    }

}