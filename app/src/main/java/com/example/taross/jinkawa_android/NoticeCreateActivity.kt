package com.example.taross.jinkawa_android

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar

class NoticeCreateActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_create)

        val toolbar = findViewById(R.id.toolbar_notice_create) as Toolbar
        toolbar.title = getString(R.string.title_notice_create)

        val createButton = findViewById(R.id.button_notice_create)
        createButton.setOnClickListener {

        }
    }
}
