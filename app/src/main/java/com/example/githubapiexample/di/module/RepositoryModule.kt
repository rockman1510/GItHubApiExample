package com.example.githubapiexample.di.module

import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.repository.*
import com.example.githubapiexample.roomdatabase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCommitsRepository(
        apiService: ApiService, appDataBase: AppDataBase
    ): CommitsRepository = CommitsRepoImpl(apiService, appDataBase)

    @Provides
    @Singleton
    fun provideCommitsByAuthorRepository(
        apiService: ApiService, appDataBase: AppDataBase
    ): CommitsByAuthorRepository =
        CommitsByAuthorRepoImpl(apiService, appDataBase)
}