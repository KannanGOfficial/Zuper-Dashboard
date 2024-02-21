package zuper.dev.android.dashboard.ui.jobs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus

class JobsViewModel(
    dataRepository: DataRepository
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
}

data class JobsUiState(
    val totalJob: Int = 0,
    val yetToStartJobList: List<JobApiModel> = emptyList(),
    val inProgressJobList: List<JobApiModel> = emptyList(),
    val cancelledJobList: List<JobApiModel> = emptyList(),
    val completedJobList: List<JobApiModel> = emptyList(),
    val inCompleteJobList: List<JobApiModel> = emptyList(),
)