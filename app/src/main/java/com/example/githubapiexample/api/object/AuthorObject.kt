package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthorObject(
    @Expose
    @SerializedName("login")
    val login: String,
    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("node_id")
    val node_id: String,
    @Expose
    @SerializedName("avatar_url")
    val avatar_url: String,
    @Expose
    @SerializedName("url")
    val url: String
)