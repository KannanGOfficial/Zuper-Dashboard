package zuper.dev.android.dashboard.data.model

import androidx.compose.ui.graphics.Color
import zuper.dev.android.dashboard.presentation.theme.DarkMintGreen
import zuper.dev.android.dashboard.presentation.theme.LightBlue
import zuper.dev.android.dashboard.presentation.theme.LightPurple
import zuper.dev.android.dashboard.presentation.theme.LightRed
import zuper.dev.android.dashboard.presentation.theme.Yellow


/**
 * A simple API model representing a Job
 *
 * Start and end date time is in ISO 8601 format - Date and time are stored in UTC timezone and
 * expected to be shown on the UI in the local timezone
 */
data class JobApiModel(
    val jobNumber: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val status: JobStatus
)

enum class JobStatus(val color: Color) {
    YetToStart(LightPurple),
    InProgress(LightBlue),
    Canceled(Yellow),
    Completed(DarkMintGreen),
    Incomplete(LightRed)
}
