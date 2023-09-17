package com.example.testworkstories.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val interceptor = HttpLoggingInterceptor()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    private fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(interceptor)
        return okHttpClientBuilder.build()
    }

    var retrofit: Api = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .baseUrl(
            BASE_URL
        ).build().create(Api::class.java)

    private companion object {
        const val BASE_URL = "https://utv.ru"
    }
}