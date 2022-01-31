package com.example.githubapiexample.api.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommitterInsideObject(
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("email")
    var email: String,
    @Expose
    @SerializedName("date")
    var date: String
)