package com.example.taross.view

/**
 * Created by taross on 2017/08/12.
 */

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.jinkawa_android.R
import com.example.taross.model.Event

class EventItemView: FrameLayout{
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var thumbnailImageView: ImageView? = null
    var departmentTextView: TextView? = null
    var valueUpdateTextView: TextView? = null
    var titleTextView: TextView? = null
    var dateStartTextView: TextView? = null
    var timeStartTextView: TextView? = null
    var locationTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.eventlist_item, this)
        thumbnailImageView = findViewById(R.id.thumbnail) as ImageView
        departmentTextView = findViewById(R.id.department) as TextView
        valueUpdateTextView = findViewById(R.id.ValueUpdateAt) as TextView
        titleTextView = findViewById(R.id.title) as TextView
        dateStartTextView = findViewById(R.id.dateStart) as TextView
        timeStartTextView = findViewById(R.id.timeStart) as TextView
        locationTextView = findViewById(R.id.location) as TextView
    }

    fun setItem(item : Event){
        departmentTextView?.text = item.department
        valueUpdateTextView?.text = item.update_date
        titleTextView?.text = item.title
        dateStartTextView?.text = item.date_start
        timeStartTextView?.text = item.time_start
        locationTextView?.text = item.location

        // 画像用処理
        thumbnailImageView?.setBackgroundColor(Color.RED)
    }
}