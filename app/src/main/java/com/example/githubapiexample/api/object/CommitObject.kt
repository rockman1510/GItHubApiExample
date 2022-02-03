package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitObject(
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("url")
    val url: String,
    @Expose
    @SerializedName("author")
    val author: AuthorInsideObject,
    @Expose
    @SerializedName("committer")
    val committer: CommitterInsideObject
)