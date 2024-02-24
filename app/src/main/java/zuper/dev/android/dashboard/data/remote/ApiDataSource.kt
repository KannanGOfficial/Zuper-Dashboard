package zuper.dev.android.dashboard.data.remote

import kotlinx.coroutines.flow.Flow
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.JobApiModel

interface ApiDataSource {
    fun observeJobs(): Flow<List<JobApiModel>>
    fun observeInvoices(): Flow<List<InvoiceApiModel>>
    fun getJobs(): List<JobApiModel>
}