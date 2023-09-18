package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("detail")
    val detail: Detail,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("status_id")
    val statusId: Int
)