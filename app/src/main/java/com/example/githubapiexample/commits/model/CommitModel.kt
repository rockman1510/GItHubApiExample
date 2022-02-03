package com.example.githubapiexample.commits.model

import android.os.Parcel
import android.os.Parcelable

data class CommitModel(
    val sha: String,
    val nodeId: String,
    val message: String,
    val url: String,
    val author: String,
    val authorEmail: String,
    val authorAvatar: String,
    val commitDate: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sha)
        parcel.writeString(nodeId)
        parcel.writeString(message)
        parcel.writeString(url)
        parcel.writeString(author)
        parcel.writeString(authorEmail)
        parcel.writeString(authorAvatar)
        parcel.writeString(commitDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommitModel> {
        override fun createFromParcel(parcel: Parcel): CommitModel {
            return CommitModel(parcel)
        }

        override fun newArray(size: Int): Array<CommitModel?> {
            return arrayOfNulls(size)
        }
    }
}