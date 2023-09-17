package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("image_logo")
    val imageLogo: String,
    @SerializedName("news_name")
    val newsName: String,
    val pages: List<Page>,
    val url: String,
    var favorite: Boolean = false
)

