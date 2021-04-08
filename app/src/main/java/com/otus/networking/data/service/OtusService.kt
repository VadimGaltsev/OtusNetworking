package com.otus.networking.data.service

import androidx.annotation.XmlRes
import com.otus.networking.data.dto.AndroidDto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OtusService {

    @GET
    @Xml
    fun someGet(): Call<AndroidDto>

    @Json
    @Multipart
    fun somePost(@Part("") part: RequestBody)

    fun someQuery(@QueryMap map: MutableMap<String, String>)

    data class QueryObject(val map: MutableMap<String, String>) {

        var name: String by map
        var phone: String by map
    }

    fun create() {
        QueryObject(mutableMapOf()).apply {
            name = "name"
            phone = "899999999"
            someQuery(this.map)
        }

    }
}