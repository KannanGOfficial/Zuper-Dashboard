package zuper.dev.android.dashboard.utils.timeformatter

import zuper.dev.android.dashboard.utils.extension.prefixArrow
import zuper.dev.android.dashboard.utils.extension.prefixIfen
import zuper.dev.android.dashboard.utils.extension.suffixComma
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Timezone {
    fun getFormattedTime(startTime: String, endTime: String): String {
        var output = ""

        val startTimeObj = LocalDateTime.parse(startTime, isoFormatter)
        val endTimeObj = LocalDateTime.parse(endTime, isoFormatter)
        val localDateTime = LocalDateTime.now()

        println("TimeObject Checking : start -> $startTimeObj end -> $endTimeObj")

        output += if (localDateTime.dayOfMonth == startTimeObj.dayOfMonth) {
            "Today".suffixComma()
        } else {
            startTimeObj.format(dateFormatter).suffixComma()
        }
        
        val endFormattedTime = endTimeObj.format(timeFormatterWithAMPM)

        output += if (startTimeObj.dayOfMonth == endTimeObj.dayOfMonth) {
            startTimeObj.format(timeFormatter) + endFormattedTime.prefixIfen()
        } else {
            startTimeObj.format(timeFormatterWithAMPM) + endTimeObj.format(dateFormatter)
                .prefixArrow().suffixComma() + endFormattedTime
        }

        return output
    }

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val timeFormatterWithAMPM =
        DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH)
    private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")

    val greetingFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd'th' yyyy")

    private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
}
