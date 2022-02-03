package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthorInsideObject(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("date")
    val date: String
)