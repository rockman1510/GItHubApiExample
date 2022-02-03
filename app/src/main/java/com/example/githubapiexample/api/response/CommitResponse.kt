package com.example.githubapiexample.api.response

import com.example.githubapiexample.api.`object`.AuthorObject
import com.example.githubapiexample.api.`object`.CommitObject
import com.example.githubapiexample.api.`object`.CommitterObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitResponse(
    @Expose
    @SerializedName("sha")
    val sha: String,
    @Expose
    @SerializedName("node_id")
    val node_id: String,

    @Expose
    @SerializedName("commit")
    val commit: CommitObject,

    @Expose
    @SerializedName("url")
    val url: String,

    @Expose
    @SerializedName("author")
    val author: AuthorObject,
    @Expose
    @SerializedName("committer")
    val committer: CommitterObject,
)