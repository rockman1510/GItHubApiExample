package com.example.githubapiexample.repository

import com.example.githubapiexample.api.ApiService
import com.example.githubapiexample.api.response.CommitResponse
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class ApiRepository(private val apiService: ApiService) {
    fun getCommitsApi(limit: Int = 25, page: Int = 1): Observable<ArrayList<CommitResponse>> {
        return apiService.getCommitsApi(limit, page)
    }

    fun getAuthorCommitsApi(
        author: String, limit: Int = 25, page: Int = 1
    ): Observable<ArrayList<CommitResponse>> {
        return apiService.getCommitsByAuthorApi(author, limit, page)
    }
}