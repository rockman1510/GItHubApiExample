package com.example.githubapiexample.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubapiexample.commits.model.CommitModel

@Entity(tableName = "CommitsEntity")
class CommitsEntity() {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    lateinit var commitList: ArrayList<CommitModel>

    constructor(commitList: ArrayList<CommitModel>) : this() {
        this.commitList = commitList
    }
}