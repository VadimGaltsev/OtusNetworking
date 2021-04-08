package com.otus.networking.data.dto

import android.graphics.Bitmap
import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Field
import java.lang.reflect.Type

enum class AndroidType {
    TABLET,
    MOBILE,
    CAR,
    FOLDABLE,
    @EnumDefault
    @SerializedName("uNKNOWN")
    UNKNOWN
}

val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
    .registerTypeHierarchyAdapter(Enum::class.java, DefaultEnumSerializer())

data class AndroidDto(
    val type: AndroidType
)

val json = """
    {
        "type" : "HOME"
    }
""".trimIndent()

annotation class EnumDefault

class DefaultEnumSerializer : JsonDeserializer<Enum<*>>, JsonSerializer<Enum<*>> {

    private val gson = Gson()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): Enum<*>? {
        val enumValue = gson.fromJson<Enum<*>>(json, typeOfT)
        return enumValue ?: run {
            val field = getDefaultEnumField(typeOfT)
            val name = field?.getAnnotation(SerializedName::class.java)?.value
            val defaultValueFromSerialized = gson.fromJson<Enum<*>>(name, typeOfT)
            defaultValueFromSerialized ?: gson.fromJson(field?.name, typeOfT)
        }
    }

    private fun getDefaultEnumField(typeOfT: Type): Field? {
        return (typeOfT as Class<*>).declaredFields.find { it.isAnnotationPresent(EnumDefault::class.java) }
    }

    override fun serialize(
        src: Enum<*>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        if (src == null || typeOfSrc == null) return null
        val field = getDefaultEnumField(typeOfSrc)
        val isDefaultValue = src.name == field?.name
        return if (isDefaultValue && field?.isAnnotationPresent(SerializedName::class.java) == false) {
            JsonPrimitive("")
        } else {
            gson.toJsonTree(src, typeOfSrc)
        }
    }
}