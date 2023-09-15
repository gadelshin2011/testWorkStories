package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Detail(
    val info: Info,
    @SerializedName("stories")
    val story: List<Story>,
    val tutorial: Tutorial
)