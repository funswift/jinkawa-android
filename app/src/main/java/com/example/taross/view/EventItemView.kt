package com.example.taross.view

/**
 * Created by taross on 2017/08/12.
 */

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.jinkawa_android.R
import com.example.taross.model.Event
import com.squareup.picasso.Picasso

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

        val idColor = item.selectDepartmentColorId(resources)
        if(idColor != -1)
            departmentTextView?.setBackgroundResource(idColor)

        // 画像用処理
        //thumbnailImageView?.setBackgroundColor(Color.RED)
        Picasso.with(context).load("https://mb.api.cloud.nifty.com/2013-09-01/applications/zUockxBwPHqxceBH/publicFiles/${item.id}.png").skipMemoryCache().error(R.drawable.jinkawa_logo).into(thumbnailImageView)

    }
}