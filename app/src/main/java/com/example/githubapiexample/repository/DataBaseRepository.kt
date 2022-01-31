package com.example.githubapiexample.repository

import androidx.lifecycle.LiveData
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.roomdatabase.AppDataBase
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity
import javax.inject.Singleton

@Singleton
class DataBaseRepository(var appDataBase: AppDataBase) {

    suspend fun renewLocalCommits(commitList: ArrayList<CommitModel>) {
        appDataBase.getCommitsDAO().reNewData(CommitsEntity(commitList))
    }

    fun getLocalCommits(): LiveData<CommitsEntity> {
        return appDataBase.getCommitsDAO().getCommits()
    }

    suspend fun renewLocalCommitsByAuthor(author: String, commitList: ArrayList<CommitModel>) {
        appDataBase.getCommitsByAuthorDAO().reNewData(CommitsByAuthorEntity(author, commitList))
    }

    fun getCommitsByAuthor(author: String): LiveData<CommitsByAuthorEntity> {
        return appDataBase.getCommitsByAuthorDAO().getCommits(author)
    }
}