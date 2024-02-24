package zuper.dev.android.dashboard.presentation.jobs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.domain.DataRepository
import zuper.dev.android.dashboard.presentation.dashboard.StatsBarInfo
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
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

            val jobList = listOf(
                StatsBarInfo(
                    color = JobStatus.Completed.color,
                    count = completedJobList.size
                ),
                StatsBarInfo(
                    color = JobStatus.YetToStart.color,
                    count = yetToStartJobList.size
                ),
                StatsBarInfo(
                    color = JobStatus.InProgress.color,
                    count = inProgressJobList.size
                ),
                StatsBarInfo(
                    color = JobStatus.Canceled.color,
                    count = cancelledJobList.size
                ),
                StatsBarInfo(
                    color = JobStatus.Incomplete.color,
                    count = inCompleteJobList.size
                ),
            )

            uiState = uiState.copy(
                totalJob = it.size,
                yetToStartJobList = yetToStartJobList,
                inProgressJobList = inProgressJobList,
                cancelledJobList = cancelledJobList,
                completedJobList = completedJobList,
                inCompleteJobList = inCompleteJobList,
                jobListInfo = jobList.sortedBy { it.count }
            )
        }
    }

    fun getJobList(index: Int): List<Job> =
        when (index) {
            JobStatus.YetToStart.ordinal -> uiState.yetToStartJobList
            JobStatus.InProgress.ordinal -> uiState.inProgressJobList
            JobStatus.Canceled.ordinal -> uiState.cancelledJobList
            JobStatus.Completed.ordinal -> uiState.completedJobList
            JobStatus.Incomplete.ordinal -> uiState.inCompleteJobList
            else -> emptyList()
        }

    fun updateSelectedTabIndex(index: Int) {
        uiState = uiState.copy(
            selectedTabIndex = index
        )
    }
}


data class JobsUiState(
    val totalJob: Int = 0,
    val yetToStartJobList: List<Job> = emptyList(),
    val inProgressJobList: List<Job> = emptyList(),
    val cancelledJobList: List<Job> = emptyList(),
    val completedJobList: List<Job> = emptyList(),
    val inCompleteJobList: List<Job> = emptyList(),
    val jobListInfo: List<StatsBarInfo> = emptyList(),
    val selectedTabIndex: Int = 0
)