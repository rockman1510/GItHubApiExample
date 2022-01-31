package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitterObject(
    @Expose
    @SerializedName("login")
    var login: String,
    @Expose
    @SerializedName("id")
    var id: Int,

    @Expose
    @SerializedName("node_id")
    var node_id: String,
    @Expose
    @SerializedName("avatar_url")
    var avatar_url: String,
    @Expose
    @SerializedName("url")
    var url: String
)
