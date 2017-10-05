package com.example.taross.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.jinkawa_android.R
import com.example.taross.model.Participant

/**
 * Created by taross on 2017/08/27.
 */
class ParticipantItemView: FrameLayout {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var nameTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.participantlist_item, this)
        nameTextView = findViewById(R.id.participant_name) as TextView
    }

    fun setItem(item : Participant){
        nameTextView?.text = item.name
    }
}