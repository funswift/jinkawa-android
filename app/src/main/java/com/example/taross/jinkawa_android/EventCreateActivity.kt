package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.WindowManager
import android.widget.*
import java.util.Calendar
import android.widget.ArrayAdapter
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBException



class EventCreateActivity : AppCompatActivity(), DoneCallback {

    //DoneCallBack インターフェースの実装
    override fun done(arg1: NCMBException?){
        val intent = Intent(applicationContext,  ListActivity::class.java)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContentView(R.layout.activity_event_create)


        val department : Spinner = findViewById(R.id.spinner_department) as Spinner
        val personalAdapter : ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item)
        personalAdapter.add("青年部")
        personalAdapter.add("総務部")


        department.adapter = personalAdapter

        val titleEditText = findViewById(R.id.edit_eventtitle) as EditText
        val departmentSpinner = findViewById(R.id.spinner_department) as Spinner
        val locationEditText = findViewById(R.id.edit_eventlocate) as EditText
        val capacityEditText = findViewById(R.id.edit_capacity) as EditText
        val officerOnlySwitch = findViewById(R.id.switch_officeronly) as Switch
        val descriptionEditText = findViewById(R.id.edit_description) as EditText

        val startDateTextView = findViewById(R.id.edit_eventdate_start) as TextView
        val startDateButton = findViewById(R.id.button_eventdate_start_picker) as Button
        val startTimeTextView =findViewById(R.id.edit_eventtime_start) as TextView
        val startTimeButton = findViewById(R.id.button_eventtime_start_picker) as Button

        val endDateTextView = findViewById(R.id.edit_eventdate_end) as TextView
        val endDateButton = findViewById(R.id.button_eventdate_end_picker) as Button
        val endTimeTextView = findViewById(R.id.edit_eventtime_end) as TextView
        val endTimeButton = findViewById(R.id.button_eventtime_end_picker) as Button

        val deadlineTextView = findViewById(R.id.edit_deadline) as TextView
        val deadlineButton = findViewById(R.id.button_deadline_picker) as Button
        val createButton = findViewById(R.id.button_eventcreate) as Button

        startDateButton.setOnClickListener {
            val date = Calendar.getInstance()
            var dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                val fixed_month = monthOfYear + 1
                startDateTextView.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        startTimeButton.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->

                startTimeTextView.text = "$hourOfDay:$minute"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true)
            dialog.show()
        }

        endDateButton.setOnClickListener {
            val date = Calendar.getInstance()
            val dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                val fixed_month = monthOfYear + 1
                endDateTextView.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        endTimeButton.setOnClickListener{
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute->

                endTimeTextView.text = "$hourOfDay:$minute"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true)
            dialog.show()
        }

        deadlineButton.setOnClickListener {
            val date = Calendar.getInstance()
            val dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                val fixed_month = monthOfYear + 1
                deadlineTextView.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val department = departmentSpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val start_date = startDateTextView.text.toString()
            val start_time = startTimeTextView.text.toString()
            val end_date = endDateTextView.text.toString()
            val end_time = endTimeTextView.text.toString()
            val capacity = capacityEditText.text.toString()
            val deadline = deadlineTextView.text.toString()
            val officer_only = officerOnlySwitch.showText


            if (true) {

            }

            val event = Event(title, "", department, start_date, start_time, end_date, end_time, description, location, capacity, deadline, "", officer_only)
            event.save(this)
            NotificationHelper.sendPush("お知らせ", "イベントが追加されました！")
        }
    }
}
