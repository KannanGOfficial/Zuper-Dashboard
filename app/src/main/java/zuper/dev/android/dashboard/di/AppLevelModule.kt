package zuper.dev.android.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zuper.dev.android.dashboard.data.DataRepositoryImpl
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import zuper.dev.android.dashboard.data.remote.ApiDataSourceImpl
import zuper.dev.android.dashboard.domain.DataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppLevelModule {

    @Provides
    @Singleton
    fun provideApiDataSource(): ApiDataSource = ApiDataSourceImpl()


    @Provides
    @Singleton
    fun provideDataRepository(apiDataSource: ApiDataSource): DataRepository =
        DataRepositoryImpl(apiDataSource)

}