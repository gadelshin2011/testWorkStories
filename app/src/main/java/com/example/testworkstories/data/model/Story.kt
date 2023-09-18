package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("image_logo")
    val imageLogo: String,
    @SerializedName("news_name")
    val newsName: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("unique_name")
    val uniqueName: String,
    var favorite: Boolean
)

