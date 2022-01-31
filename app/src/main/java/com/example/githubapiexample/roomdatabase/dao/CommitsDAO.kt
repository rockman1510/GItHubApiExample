package com.example.githubapiexample.roomdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity

@Dao
interface CommitsDAO {
    @Insert
    fun insertCommits(vararg commitsEntity: CommitsEntity)

    @Query("SELECT * FROM CommitsEntity")
    fun getCommits(): LiveData<CommitsEntity>

    @Query("DELETE FROM CommitsEntity")
    fun deleteAll() : Int

    @Transaction
    suspend fun reNewData(commitsEntity: CommitsEntity){
        deleteAll()
        insertCommits(commitsEntity)
    }
}