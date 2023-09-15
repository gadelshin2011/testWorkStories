package com.example.testworkstories.data.network

import com.example.testworkstories.data.model.Stories
import retrofit2.Response
import retrofit2.http.GET

interface InterfaceApi {
    @GET("/api/v0/stories/")
    suspend fun getStories():Response<Stories>
}