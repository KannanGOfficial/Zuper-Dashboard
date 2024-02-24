package zuper.dev.android.dashboard.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import zuper.dev.android.dashboard.domain.DataRepository
import zuper.dev.android.dashboard.presentation.jobs.Job
import zuper.dev.android.dashboard.presentation.jobs.toJob
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val apiDataSource: ApiDataSource) :
    DataRepository {

    /**
     * This API returns jobs in realtime using which stats can be computed
     */
    override fun observeJobs(): Flow<List<Job>> {
        return apiDataSource.observeJobs().map { it.map(JobApiModel::toJob) }
    }

    /**
     * This API returns invoices in realtime using which stats can be computed
     */
    override fun observeInvoices(): Flow<List<InvoiceApiModel>> {
        return apiDataSource.observeInvoices()
    }

    /**
     * This API returns random jobs every time invoked
     */
    override fun getJobs(): List<Job> {
        return apiDataSource.getJobs().map(JobApiModel::toJob)
    }
}
