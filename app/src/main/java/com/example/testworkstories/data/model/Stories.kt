package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Stories(
    val detail: Detail,
    val error: String,
    val status: String,
    @SerializedName("status_id")
    val statusId: Int
)