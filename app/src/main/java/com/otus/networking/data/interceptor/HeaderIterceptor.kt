package com.otus.networking.data.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    val token = "123"
    val tokenRequest = Request.Builder().build()

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder()
            .addHeader("token", chain.proceed(tokenRequest).body()?.toString())
        return chain.proceed(
            chain.request().newBuilder().build()
        )
    }
}