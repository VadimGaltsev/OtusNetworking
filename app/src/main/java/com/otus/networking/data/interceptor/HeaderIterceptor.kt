package com.otus.networking.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //todo add header
        return chain.proceed(
            chain.request().newBuilder().build()
        )
    }
}