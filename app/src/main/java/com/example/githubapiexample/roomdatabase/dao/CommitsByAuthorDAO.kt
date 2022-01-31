package com.example.githubapiexample.roomdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity

@Dao
interface CommitsByAuthorDAO {
    @Insert
    fun insertCommits(vararg commitsByAuthorEntity: CommitsByAuthorEntity)

    @Query("SELECT * FROM CommitsByAuthorEntity WHERE author = :authorEmail")
    fun getCommits(authorEmail: String): LiveData<CommitsByAuthorEntity>

    @Query("DELETE FROM CommitsByAuthorEntity WHERE author = :authorEmail")
    fun deleteByAuthor(authorEmail: String): Int

    @Transaction
    suspend fun reNewData(commitsByAuthorEntity: CommitsByAuthorEntity){
        deleteByAuthor(commitsByAuthorEntity.author)
        insertCommits(commitsByAuthorEntity)
    }
}