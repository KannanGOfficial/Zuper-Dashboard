package zuper.dev.android.dashboard.utils.timeformatter

import zuper.dev.android.dashboard.utils.extension.prefixArrow
import zuper.dev.android.dashboard.utils.extension.prefixIfen
import zuper.dev.android.dashboard.utils.extension.suffixComma
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeFormatter {
    fun getFormattedTime(startTime: String, endTime: String): String {

        val startDateTimeFormatted = LocalDateTime.parse(startTime, isoFormatter)
        val endDateTimeFormatted = LocalDateTime.parse(endTime, isoFormatter)
        val endTimeFormatted = endDateTimeFormatted.format(timeFormatterWithAMPM)


        var result = isDateEqualsTodayAddToday(startDateTimeFormatted.dayOfMonth) {
            startDateTimeFormatted.format(dateFormatter).suffixComma()
        }

        result += when (startDateTimeFormatted.dayOfMonth) {
            endDateTimeFormatted.dayOfMonth -> {
                startDateTimeFormatted.format(timeFormatter) + endTimeFormatted.prefixIfen()
            }

            else -> {
                startDateTimeFormatted.format(timeFormatterWithAMPM) + endDateTimeFormatted.format(
                    dateFormatter
                )
                    .prefixArrow().suffixComma() + endTimeFormatted
            }
        }
        return result
    }

    private fun isDateEqualsTodayAddToday(dayOfMonth: Int, elseCallBack: () -> String): String =
        if (LocalDateTime.now().dayOfMonth == dayOfMonth)
            "Today".suffixComma()
        else
            elseCallBack()


    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val timeFormatterWithAMPM =
        DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH)

    private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")

    val greetingFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd'th' yyyy")

    private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
}
