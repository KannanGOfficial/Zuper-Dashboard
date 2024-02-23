package zuper.dev.android.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppLevelModule {

    @Provides
    @Singleton
    fun provideApiDataSource(): ApiDataSource = ApiDataSource()


    @Provides
    @Singleton
    fun provideDataRepository(apiDataSource: ApiDataSource) =
        DataRepository(apiDataSource)

}