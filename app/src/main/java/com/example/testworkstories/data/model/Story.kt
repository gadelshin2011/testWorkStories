package com.example.testworkstories.data.model

import com.google.gson.annotations.SerializedName

data class Story(
    val date: Int,
    val description: String,
    @SerializedName("exp_date")
    val expDate: Int,
    @SerializedName("image_logo")
    val imageLogo: String,
    @SerializedName("is_add")
    val isAdd: Boolean,
    @SerializedName("material_url")
    val materialUrl: String,
    @SerializedName("news_name")
    val newsName: String,
    val pages: List<Page>,
    @SerializedName("text_url")
    val textUrl: String,
    @SerializedName("unique_name")
    val uniqueName: String,
    val url: String
)
