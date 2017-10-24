package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.Editable
import android.util.Log
import android.widget.*
import java.util.Calendar
import android.widget.ArrayAdapter
import com.example.taross.model.Event
import org.w3c.dom.Text


class EventCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_create)


        val department : Spinner = findViewById(R.id.spinner_department) as Spinner
        val personalAdapter : ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item)
        personalAdapter.add("青年部")
        personalAdapter.add("総務部")

        /*

        プロジェクトに余裕ができたらリファクタリング予定

        プロジェクト

                            余裕ができたら

                                                            リファクタリング




        */


        department.adapter = personalAdapter

        val start_date = findViewById(R.id.edit_eventdate_start) as TextView
        val start_date_button = findViewById(R.id.button_eventdate_start_picker) as Button
        val start_time =findViewById(R.id.edit_eventtime_start) as TextView
        val start_time_button = findViewById(R.id.button_eventtime_start_picker) as Button

        val end_date = findViewById(R.id.edit_eventdate_end) as TextView
        val end_date_button = findViewById(R.id.button_eventdate_end_picker) as Button
        val end_time =findViewById(R.id.edit_eventtime_end) as TextView
        val end_time_button = findViewById(R.id.button_eventtime_end_picker) as Button

        val deadline = findViewById(R.id.edit_deadline) as TextView
        val deadline_button = findViewById(R.id.button_deadline_picker) as Button
        val create_button = findViewById(R.id.button_eventcreate) as Button


        start_date_button.setOnClickListener {
            val date = Calendar.getInstance()
            var dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                val fixed_month = monthOfYear + 1
                start_date.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        start_time_button.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->

                start_time.text = "$hourOfDay:$minute"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true)
            dialog.show()
        }

        end_date_button.setOnClickListener {
            val date = Calendar.getInstance()
            val dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                end_date.text = "$year/$monthOfYear/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        end_time_button.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->

                end_time.text = "$hourOfDay:$minute"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true)
            dialog.show()
        }

        deadline_button.setOnClickListener {
            val date = Calendar.getInstance()
            val dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                deadline.text = "$year/$monthOfYear/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        create_button.setOnClickListener{
            val title = "none"
            val department = ""
            val location = ""
            val capacity = ""
            val deadline =""
            val officer_only = false

            val event =Event(title, "" ,department, start_date.text.toString(), start_time.text.toString(), end_date.text.toString(), end_time.text.toString(), location, capacity, deadline, officer_only)
            event.save()

        }


    }


}
