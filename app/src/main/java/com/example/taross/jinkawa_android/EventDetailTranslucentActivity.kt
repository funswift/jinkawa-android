package com.example.taross.jinkawa_android

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.example.taross.model.Event
import com.squareup.picasso.Picasso

/**
 * Created by y_snkw on 2017/11/24.
 */
class EventDetailTranslucentActivity: Activity() {

    val event : Event by lazy{intent.getParcelableExtra<Event>("EVENT_EXTRA")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail_translucent)

        val imageView = findViewById(R.id.imageView_translucent) as ImageView
        Picasso.with(applicationContext).load("https://mb.api.cloud.nifty.com/2013-09-01/applications/zUockxBwPHqxceBH/publicFiles/${event.id}.png").into(imageView)

        val closeButton = findViewById(R.id.button_translucent) as ImageButton
        closeButton.setOnClickListener {
            finish()
        }
    }
}