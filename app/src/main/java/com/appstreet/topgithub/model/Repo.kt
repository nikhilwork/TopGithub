package com.appstreet.topgithub.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name") @Expose var name: String,
    @SerializedName("description") @Expose var description: String,
    @SerializedName("url") @Expose var url: String
)
