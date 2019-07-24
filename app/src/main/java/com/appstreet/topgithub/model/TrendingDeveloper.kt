package com.appstreet.topgithub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrendingDeveloper(
    @SerializedName("username") @Expose var username: String?,
    @SerializedName("name") @Expose var name: String?,
    @SerializedName("type") @Expose var type: String?,
    @SerializedName("url") @Expose var url: String?,
    @SerializedName("avatar") @Expose var avatar: String?,
    @SerializedName("repo") @Expose var repo: Repo?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("repo")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrendingDeveloper> {
        override fun createFromParcel(parcel: Parcel): TrendingDeveloper {
            return TrendingDeveloper(parcel)
        }

        override fun newArray(size: Int): Array<TrendingDeveloper?> {
            return arrayOfNulls(size)
        }
    }
}
