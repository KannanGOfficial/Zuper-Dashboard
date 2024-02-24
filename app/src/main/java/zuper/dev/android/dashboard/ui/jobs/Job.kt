package zuper.dev.android.dashboard.ui.jobs

import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.utils.timeformatter.TimeFormatter

data class Job(
    val jobNumber: Int,
    val title: String,
    val jobTimeDetails: String,
    val status: JobStatus
)

fun JobApiModel.toJob() = Job(
    jobNumber = jobNumber,
    title = title,
    status = status,
    jobTimeDetails = TimeFormatter.getFormattedTime(
        startTime = startTime,
        endTime = endTime
    )
)