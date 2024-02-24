package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dataRepository: DataRepository
) : ViewModel() {

    var uiState by mutableStateOf(DashBoardUiState())
        private set

    init {
        dataRepository.observeJobs()
            .distinctUntilChanged()
            .onEach { job ->
                val totalJobs = job.size
                val completedJobs = job.count { it.status == JobStatus.Completed }
                val yetToStart = job.count { it.status == JobStatus.YetToStart }
                val inProgress = job.count { it.status == JobStatus.InProgress }
                val cancelled = job.count { it.status == JobStatus.Canceled }
                val inCompleted = job.count { it.status == JobStatus.Incomplete }

                val jobList = listOf(
                    StatsBarInfo(
                        color = JobStatus.Completed.color,
                        count = completedJobs
                    ),
                    StatsBarInfo(
                        color = JobStatus.YetToStart.color,
                        count = yetToStart
                    ),
                    StatsBarInfo(
                        color = JobStatus.InProgress.color,
                        count = inProgress
                    ),
                    StatsBarInfo(
                        color = JobStatus.Canceled.color,
                        count = cancelled
                    ),
                    StatsBarInfo(
                        color = JobStatus.Incomplete.color,
                        count = inCompleted
                    ),
                )

                uiState = uiState.copy(
                    totalJobs = totalJobs,
                    completedJobs = completedJobs,
                    yetToStart = yetToStart,
                    inProgress = inProgress,
                    cancelled = cancelled,
                    inCompleted = inCompleted,
                    jobListInfo = jobList.sortedBy { it.count }
                )
            }
            .launchIn(viewModelScope)

        dataRepository.observeInvoices()
            .onEach { invoice ->
                val totalValue = invoice.sumOf { it.total }
                val paid = invoice.filter { it.status == InvoiceStatus.Paid }.sumOf { it.total }
                val draft =
                    invoice.filter { it.status == InvoiceStatus.Draft }.sumOf { it.total }
                val pending =
                    invoice.filter { it.status == InvoiceStatus.Pending }.sumOf { it.total }
                val badDebit =
                    invoice.filter { it.status == InvoiceStatus.BadDebt }.sumOf { it.total }

                val invoiceList = listOf(
                    StatsBarInfo(
                        color = InvoiceStatus.Draft.color,
                        count = draft
                    ),
                    StatsBarInfo(
                        color = InvoiceStatus.Pending.color,
                        count = pending
                    ),
                    StatsBarInfo(
                        color = InvoiceStatus.Paid.color,
                        count = paid
                    ),
                    StatsBarInfo(
                        color = InvoiceStatus.BadDebt.color,
                        count = badDebit
                    ),
                )

                uiState = uiState.copy(
                    totalValue = totalValue,
                    paid = paid,
                    draft = draft,
                    pending = pending,
                    badDebit = badDebit,
                    invoiceListInfo = invoiceList.sortedBy { it.count }
                )
            }.launchIn(viewModelScope)
    }
}


data class DashBoardUiState(
    val totalJobs: Int = 0,
    val completedJobs: Int = 0,
    val yetToStart: Int = 0,
    val inProgress: Int = 0,
    val cancelled: Int = 0,
    val inCompleted: Int = 0,
    val totalValue: Int = 0,
    val draft: Int = 0,
    val pending: Int = 0,
    val paid: Int = 0,
    val badDebit: Int = 0,
    val jobListInfo: List<StatsBarInfo> = emptyList(),
    val invoiceListInfo: List<StatsBarInfo> = emptyList()
)

