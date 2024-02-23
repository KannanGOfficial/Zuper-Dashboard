package zuper.dev.android.dashboard.ui.jobs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.utils.extension.prefixArrow
import zuper.dev.android.dashboard.utils.extension.prefixIfen
import zuper.dev.android.dashboard.utils.extension.suffixComma
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JobsViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {

    var uiState by mutableStateOf(JobsUiState())
        private set

    init {
        dataRepository.getJobs().also {
            val yetToStartJobList = it.filter { it.status == JobStatus.YetToStart }
            val inProgressJobList = it.filter { it.status == JobStatus.InProgress }
            val cancelledJobList = it.filter { it.status == JobStatus.Canceled }
            val completedJobList = it.filter { it.status == JobStatus.Completed }
            val inCompleteJobList = it.filter { it.status == JobStatus.Incomplete }

            uiState = uiState.copy(
                totalJob = it.size,
                yetToStartJobList = yetToStartJobList,
                inProgressJobList = inProgressJobList,
                cancelledJobList = cancelledJobList,
                completedJobList = completedJobList,
                inCompleteJobList = inCompleteJobList,
            )
        }
    }

    fun getJobList(index: Int): List<JobApiModel> =
        when (index) {
            JobStatus.YetToStart.ordinal -> uiState.yetToStartJobList
            JobStatus.InProgress.ordinal -> uiState.inProgressJobList
            JobStatus.Canceled.ordinal -> uiState.cancelledJobList
            JobStatus.Completed.ordinal -> uiState.completedJobList
            JobStatus.Incomplete.ordinal -> uiState.inCompleteJobList
            else -> emptyList()
        }
}


data class JobsUiState(
    val totalJob: Int = 0,
    val yetToStartJobList: List<JobApiModel> = emptyList(),
    val inProgressJobList: List<JobApiModel> = emptyList(),
    val cancelledJobList: List<JobApiModel> = emptyList(),
    val completedJobList: List<JobApiModel> = emptyList(),
    val inCompleteJobList: List<JobApiModel> = emptyList(),
)

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

        output += startTimeObj.format(timeFormatter)

        val endFormattedTime = endTimeObj.format(timeFormatter)

        output += if(startTimeObj.dayOfMonth == endTimeObj.dayOfMonth){
            endFormattedTime.prefixIfen()
        }else{
            endTimeObj.format(dateFormatter).prefixArrow().suffixComma() + endFormattedTime
        }

        return output
    }

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
}