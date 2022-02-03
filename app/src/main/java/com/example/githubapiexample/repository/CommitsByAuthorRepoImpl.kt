package com.example.githubapiexample.repository

import androidx.lifecycle.LiveData
import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.roomdatabase.AppDataBase
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CommitsByAuthorRepoImpl @Inject constructor(
    private val apiService: ApiService, private val dataBase: AppDataBase
) : CommitsByAuthorRepository {
    override suspend fun getAuthorCommitsApi(
        author: String, limit: Int, page: Int
    ): Response<ArrayList<CommitResponse>> {
        return withContext(Dispatchers.IO) {
            apiService.getCommitsByAuthorApi(author, limit, page)
        }
    }

    override suspend fun getLocalCommitsByAuthor(author: String): LiveData<CommitsByAuthorEntity> {
        return withContext(Dispatchers.IO) {
            dataBase.getCommitsByAuthorDAO().getCommits(author)
        }
    }

    override suspend fun renewLocalCommitsByAuthor(
        author: String, dataList: ArrayList<CommitModel>
    ): Boolean {
        return withContext(Dispatchers.IO) {
            dataBase.getCommitsByAuthorDAO().reNewData(CommitsByAuthorEntity(author, dataList))
            return@withContext true
        }
    }
}