package zuper.dev.android.dashboard.domain

import kotlinx.coroutines.flow.Flow
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.presentation.jobs.Job

interface DataRepository {
    fun observeJobs(): Flow<List<Job>>
    fun observeInvoices(): Flow<List<InvoiceApiModel>>
    fun getJobs(): List<Job>

}