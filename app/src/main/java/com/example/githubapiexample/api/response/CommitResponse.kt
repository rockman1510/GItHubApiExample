package com.example.githubapiexample.api.response

import com.example.githubapiexample.api.`object`.AuthorObject
import com.example.githubapiexample.api.`object`.CommitObject
import com.example.githubapiexample.api.`object`.CommitterObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitResponse(
    @Expose
    @SerializedName("sha")
    var sha: String,
    @Expose
    @SerializedName("node_id")
    var node_id: String,

    @Expose
    @SerializedName("commit")
    var commit: CommitObject,

    @Expose
    @SerializedName("url")
    var url: String,

    @Expose
    @SerializedName("author")
    var author: AuthorObject,
    @Expose
    @SerializedName("committer")
    var committer: CommitterObject,
)