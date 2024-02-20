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
import zuper.dev.android.dashboard.utils.extension.sum

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
                val totalValue = it.sum { it.total }
                val paid = it.filter { it.status == InvoiceStatus.Paid }.sum { it.total }
                val draft =
                    it.filter { it.status == InvoiceStatus.Draft }.sum { it.total }
                val pending =
                    it.filter { it.status == InvoiceStatus.Pending }.sum { it.total }
                val badDebit =
                    it.filter { it.status == InvoiceStatus.BadDebt }.sum { it.total }

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
    val totalValue: Long = 0L,
    val draft: Long = 0L,
    val pending: Long = 0L,
    val paid: Long = 0L,
    val badDebit: Long = 0L
)