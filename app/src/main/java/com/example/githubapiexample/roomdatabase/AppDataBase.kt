package com.example.githubapiexample.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubapiexample.BuildConfig
import com.example.githubapiexample.roomdatabase.converter.CommitListConverter
import com.example.githubapiexample.roomdatabase.dao.CommitsByAuthorDAO
import com.example.githubapiexample.roomdatabase.dao.CommitsDAO
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity

@Database(entities = [CommitsEntity::class, CommitsByAuthorEntity::class], version = 1)
@TypeConverters(CommitListConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getCommitsDAO(): CommitsDAO
    abstract fun getCommitsByAuthorDAO(): CommitsByAuthorDAO

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getRoomDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java,
                    BuildConfig.APPLICATION_ID + AppDataBase::class.java.simpleName
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}