package zuper.dev.android.dashboard.utils.extension

import androidx.compose.ui.text.toLowerCase
import java.util.Locale

fun String.prefixDollar() =
    "$$this"

fun String.formatTitleCase(): String {
    return if (isNotBlank() && isNotEmpty()) {
        var firstLetter = get(0).uppercaseChar()
        var substring = substring(1).lowercase(Locale.ROOT)
        firstLetter + substring
    } else
        ""
}