package com.otus.networking.base

import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.otus.networking.OtusApplication
import com.otus.networking.R

abstract class BaseOtusActivity : AppCompatActivity() {

    private val fieldGroup by lazy { findViewById<ViewGroup>(R.id.fields_group) }
    private val gson by lazy {
        GsonBuilder()
            .setDateFormat("секунды ss, минуты mm, день dd, месяц MM, год yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create()
    }

    protected val retrofit by lazy { (application as OtusApplication).retrofit }

    protected fun <T> populate(data: T) {
        val map = gson.fromJson<Map<String, Any>>(gson.toJson(data), HashMap::class.java)
        map.forEach {
            val objectField = "${it.key} : ${it.value}"
            val view = TextView(this)
                .apply {
                    text = objectField
                }
            fieldGroup.addView(view)
        }
    }
}