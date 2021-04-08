package com.otus.networking

import android.app.Application
import com.google.gson.Gson
import com.otus.networking.data.service.Json
import com.otus.networking.data.service.Xml
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import java.lang.reflect.Type
import kotlin.reflect.KClass

class OtusApplication : Application() {

    lateinit var retrofit: Retrofit
    var isFeatureEnabled = false

    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(MultiConverterFactory().apply {
                converters[Xml::class] = JaxbConverterFactory.create()
                converters[Json::class] = GsonConverterFactory.create(Gson())
            })
            .client(client)
            .build()
        if (isFeatureEnabled) {
            Retrofit.Builder().baseUrl("").build()
            retrofit.newBuilder().baseUrl("other url").build()
        }
    }
}

class MultiConverterFactory : Converter.Factory() {

    val converters = HashMap<KClass<out Annotation>, Converter.Factory>()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return super.responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val converterAnnotation =
            methodAnnotations.first { converters.contains(it.annotationClass) }.annotationClass
        return converters[converterAnnotation]?.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }
}
