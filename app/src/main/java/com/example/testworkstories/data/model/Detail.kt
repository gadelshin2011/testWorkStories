package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("stories")
    val story: List<Story>

)