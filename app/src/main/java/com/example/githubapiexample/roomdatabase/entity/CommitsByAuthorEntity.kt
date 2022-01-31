package com.example.githubapiexample.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubapiexample.commits.model.CommitModel

@Entity(tableName = "CommitsByAuthorEntity")
class CommitsByAuthorEntity() {

    @PrimaryKey
    var author: String = ""
    lateinit var commitList: ArrayList<CommitModel>

    constructor(author: String, commitList: ArrayList<CommitModel>) : this() {
        this.author = author
        this.commitList = commitList
    }
}