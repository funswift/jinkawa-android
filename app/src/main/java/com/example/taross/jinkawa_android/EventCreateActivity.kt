package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.util.Log
import java.util.Calendar



class EventCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_create)

        val 

        val start_date_button = findViewById(R.id.button_eventdate_picker) as Button
        start_date_button.setOnClickListener {
            val date = Calendar.getInstance()
            val dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                Log.d("year", year.toString())
                Log.d("month",monthOfYear.toString())
                Log.d("day", dayOfMonth.toString() )
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }


}
