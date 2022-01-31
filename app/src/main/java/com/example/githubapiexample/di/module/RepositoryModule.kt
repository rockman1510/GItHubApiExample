package com.example.githubapiexample.di.module

import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.repository.ApiRepository
import com.example.githubapiexample.repository.DataBaseRepository
import com.example.githubapiexample.roomdatabase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository = ApiRepository(apiService)

    @Provides
    @Singleton
    fun provideDataBaseRepository(appDataBase: AppDataBase): DataBaseRepository = DataBaseRepository(appDataBase)
}