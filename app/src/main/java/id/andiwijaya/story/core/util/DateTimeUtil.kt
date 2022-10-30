package id.andiwijaya.story.core.util

import android.content.Context
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.DateFormat.DATE_TIME_SHORT_FORMAT
import id.andiwijaya.story.core.Constants.DateFormat.RESPONSE_DATE_FORMAT
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.concurrent.TimeUnit

object DateTimeUtil {

    fun convertDateAndTime(dateTime: String): String = LocalDateTime.parse(
        dateTime, DateTimeFormatter.ofPattern(RESPONSE_DATE_FORMAT, Locale.ENGLISH)
    ).format(DateTimeFormatter.ofPattern(DATE_TIME_SHORT_FORMAT))

    fun timeDifference(dateTime: String, context: Context): String {
        val date = SimpleDateFormat(RESPONSE_DATE_FORMAT, Locale.ENGLISH).parse(dateTime)
        val millis: Long = date?.time ?: 0L
        val diffMillis = Date().time - millis

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        return if (seconds < 60) {
            context.getString(R.string.second_format, seconds)
        } else if (minutes < 60) {
            context.getString(R.string.minute_format, minutes)
        } else if (hours < 24) {
            context.getString(R.string.hour_format, hours)
        } else {
            convertDateAndTime(dateTime)
        }
    }

}