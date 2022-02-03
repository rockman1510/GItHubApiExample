package com.example.githubapiexample.repository

import androidx.lifecycle.LiveData
import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.roomdatabase.AppDataBase
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CommitsRepoImpl @Inject constructor(
    private val apiService: ApiService, private val dataBase: AppDataBase
) : CommitsRepository {
    override suspend fun getCommitsApi(limit: Int, page: Int): Response<ArrayList<CommitResponse>> {
        return withContext(Dispatchers.IO) {
            apiService.getCommitsApi(limit, page)
        }
    }

    override suspend fun getLocalCommits(): LiveData<CommitsEntity> {
        return withContext(Dispatchers.IO) {
            dataBase.getCommitsDAO().getCommits()
        }
    }

    override suspend fun renewLocalCommits(commitList: ArrayList<CommitModel>): Boolean {
        return withContext(Dispatchers.IO) {
            dataBase.getCommitsDAO().reNewData(CommitsEntity(commitList))
            return@withContext true
        }
    }
}