package com.example.githubapiexample.roomdatabase.converter

import androidx.room.TypeConverter
import com.example.githubapiexample.commits.model.CommitModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommitListConverter {
    @TypeConverter
    fun fromCommitList(commitList: ArrayList<CommitModel>?): String {
        return Gson().toJson(commitList).toString()
    }

    @TypeConverter
    fun toCommitList(string: String): ArrayList<CommitModel> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<CommitModel>>() {}.type)
    }
}