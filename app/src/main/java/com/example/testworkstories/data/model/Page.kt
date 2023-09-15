package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Page(
    val duration: String,
    @SerializedName("file_type")
    val fileType: String,
    @SerializedName("file_url")
    val fileUrl: String,
    val id: Int
)
