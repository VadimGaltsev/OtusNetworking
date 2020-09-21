package com.otus.networking

import android.os.Bundle
import android.widget.Button
import com.otus.networking.base.BaseOtusActivity
import com.otus.networking.data.dto.AndroidDto
import java.util.*

class MainActivity : BaseOtusActivity() {

    private val getButton by lazy { findViewById<Button>(R.id.get) }
    private val postButton by lazy { findViewById<Button>(R.id.post) }
    private val deleteButton by lazy { findViewById<Button>(R.id.delete) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getButton.setOnClickListener {
            populate(AndroidDto(Date()))
        }
    }
}
