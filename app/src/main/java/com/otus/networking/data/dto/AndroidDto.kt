package com.otus.networking.data.dto

import android.graphics.Bitmap
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class AndroidDto(
    @SerializedName("alter name", alternate = ["other name"])
    @Expose
    val id: Date,
    @Expose
    val bitmap: Bitmap? = null
)