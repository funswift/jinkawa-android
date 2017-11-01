package com.example.taross.jinkawa_android

import android.app.Activity
import android.os.Bundle

class NoticeCreateActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_create)

        val createButton = findViewById(R.id.button_notice_create)
        createButton.setOnClickListener {

        }
    }
}
