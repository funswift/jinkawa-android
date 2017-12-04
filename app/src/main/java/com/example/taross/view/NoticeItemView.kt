package com.example.taross.view

import android.content.Context
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
    var departmentTextView: TextView? = null
    var valueUpdateTextView: TextView? = null
    var titleTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.noticelist_item, this)
        thumbnailImageView = findViewById(R.id.thumbnail) as ImageView
        departmentTextView = findViewById(R.id.department) as TextView
        valueUpdateTextView = findViewById(R.id.ValueUpdateAt) as TextView
        titleTextView = findViewById(R.id.title) as TextView
    }

    fun setItem(item : Notice){
        departmentTextView?.text = item.department
        valueUpdateTextView?.text = item.update_date
        titleTextView?.text = item.title

        //部署背景色処理
        val idColor = item.selectDepartmentColorId(resources)
        if(idColor != -1)
            departmentTextView?.setBackgroundResource(idColor)

        // 画像用処理
        val idIcon = item.typeSelectedToIcon(item.type, resources)
        if(idIcon != -1)
            thumbnailImageView?.setImageResource(idIcon)
    }
}