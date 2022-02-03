package com.example.githubapiexample.repository

import androidx.lifecycle.LiveData
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import retrofit2.Response

interface CommitsByAuthorRepository {
    suspend fun getAuthorCommitsApi(
        author: String, limit: Int, page: Int
    ): Response<ArrayList<CommitResponse>>

    suspend fun getLocalCommitsByAuthor(author: String): LiveData<CommitsByAuthorEntity>
    suspend fun renewLocalCommitsByAuthor(author: String, dataList: ArrayList<CommitModel>): Boolean
}