package com.example.taross.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.jinkawa_android.R
import com.example.taross.model.Notice

/**
 * Created by taross on 2017/08/14.
 */

class NoticeItemView: FrameLayout{

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var thumbnailImageView: ImageView? = null

    var titleTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.eventlist_item, this)
        thumbnailImageView = findViewById(R.id.thumbnail) as ImageView
        titleTextView = findViewById(R.id.title) as TextView
    }

    fun setItem(item : Notice){
        titleTextView?.text = item.title

        // 画像用処理
        thumbnailImageView?.setBackgroundColor(Color.BLUE)
    }
}