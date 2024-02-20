package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus

class DashboardViewModel(
    dataRepository: DataRepository
) : ViewModel() {

    var uiState by mutableStateOf(DashBoardUiState())
        private set

    init {
        dataRepository.observeJobs()
            .distinctUntilChanged()
            .onEach {
                val totalJobs = it.size
                val completedJobs = it.count { it.status == JobStatus.Completed }
                val yetToStart = it.count { it.status == JobStatus.YetToStart }
                val inProgress = it.count { it.status == JobStatus.InProgress }
                val cancelled = it.count { it.status == JobStatus.Canceled }
                val inCompleted = it.count { it.status == JobStatus.Incomplete }

                uiState = uiState.copy(
                    totalJobs = totalJobs,
                    completedJobs = completedJobs,
                    yetToStart = yetToStart,
                    inProgress = inProgress,
                    cancelled = cancelled,
                    inCompleted = inCompleted
                )
            }
            .launchIn(viewModelScope)

        dataRepository.observeInvoices()
            .onEach {
                val totalValue = it.sumOf { it.total }.toLong()
                val paid = it.filter { it.status == InvoiceStatus.Paid }.sumOf { it.total }.toLong()
                val draft =
                    it.filter { it.status == InvoiceStatus.Draft }.sumOf { it.total }.toLong()
                val pending =
                    it.filter { it.status == InvoiceStatus.Pending }.sumOf { it.total }.toLong()
                val badDebit =
                    it.filter { it.status == InvoiceStatus.BadDebt }.sumOf { it.total }.toLong()

                uiState = uiState.copy(
                    totalValue = totalValue,
                    paid = paid,
                    draft = draft,
                    pending = pending,
                    badDebit = badDebit
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
    val totalValue: Long = 0,
    val draft: Long = 0,
    val pending: Long = 0,
    val paid: Long = 0,
    val badDebit: Long = 0
)