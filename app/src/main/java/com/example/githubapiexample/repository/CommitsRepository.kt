package com.example.githubapiexample.repository

import androidx.lifecycle.LiveData
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity
import retrofit2.Response

interface CommitsRepository {
    suspend fun getCommitsApi(limit: Int, page: Int): Response<ArrayList<CommitResponse>>
    suspend fun getLocalCommits(): LiveData<CommitsEntity>
    suspend fun renewLocalCommits(commitList: ArrayList<CommitModel>): Boolean
}