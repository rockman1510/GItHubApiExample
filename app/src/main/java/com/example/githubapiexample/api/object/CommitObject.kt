package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitObject(
    @Expose
    @SerializedName("message")
    var message: String,
    @Expose
    @SerializedName("url")
    var url: String,
    @Expose
    @SerializedName("author")
    var author: AuthorInsideObject,
    @Expose
    @SerializedName("committer")
    var committer: CommitterInsideObject
)