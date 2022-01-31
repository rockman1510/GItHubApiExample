package com.example.githubapiexample.commits.model

import android.os.Parcel
import android.os.Parcelable

data class CommitModel(
    var sha: String = "", var nodeId: String = "",
    var message: String = "",
    var url: String = "",
    var author: String = "",
    var authorEmail: String = "",
    var authorAvatar: String = "",
    var commitDate: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
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