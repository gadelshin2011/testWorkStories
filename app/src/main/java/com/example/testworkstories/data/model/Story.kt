package com.example.testworkstories.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story_table")
data class Story(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo
    @SerializedName("image_logo")
    val imageLogo: String,
    @ColumnInfo("news_name")
    @SerializedName("news_name")
    val newsName: String,
    @ColumnInfo
    @SerializedName("url")
    val url: String,
    @ColumnInfo
    var favorite: Boolean = false
){

    @SerializedName("pages")
    @Ignore val pages: List<Page> = TODO()
}

