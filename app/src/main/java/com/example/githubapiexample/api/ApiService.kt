package com.example.githubapiexample.api

import com.example.githubapiexample.api.response.CommitResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/repos/android/platform_build/commits")
    suspend fun getCommitsApi(
        @Query("per_page") limit: Int = 25, @Query("page") page: Int = 1
    ): Response<ArrayList<CommitResponse>>

    @GET("/repos/android/platform_build/commits")
    suspend fun getCommitsByAuthorApi(
        @Query("author") author: String,
        @Query("per_page") limit: Int = 25,
        @Query("page") page: Int = 1
    ): Response<ArrayList<CommitResponse>>
}