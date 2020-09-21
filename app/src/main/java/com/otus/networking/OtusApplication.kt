package com.otus.networking

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class OtusApplication : Application() {

    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient.Builder()
            .addInterceptor(/* todo add interceptor */ null)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://78.107.253.32")
            .client(client)
            .build()
    }
}
