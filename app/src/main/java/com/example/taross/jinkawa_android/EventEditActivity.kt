package com.example.taross.jinkawa_android

/**
 * Created by y_snkw on 2017/11/13.
 */

import android.os.Bundle
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.*
import android.widget.ArrayAdapter
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.NCMBException
import com.squareup.picasso.Picasso

class EventEditActivity: EventCreateActivity() {

    val event :Event by lazy{intent.getParcelableExtra<Event>("EVENT_EXTRA")}

    //DoneCallBack インターフェースの実装
    override fun done(arg1: NCMBException?) {
        val intent = Intent(applicationContext, EventDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar_event_create) as Toolbar
        toolbar.title = getString(R.string.title_event_edit)

        val departmentSpinner : Spinner = findViewById(R.id.spinner_department) as Spinner
        val personalAdapter : ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_departments))
        departmentSpinner.adapter = personalAdapter
        val spinnerIndex = setSpinnerSelection(personalAdapter)
        var officer = false

        val titleEditText = findViewById(R.id.edit_eventtitle) as EditText
        val locationEditText = findViewById(R.id.edit_eventlocate) as EditText
        val capacityEditText = findViewById(R.id.edit_capacity) as EditText
        val officerOnlySwitch = findViewById(R.id.switch_officeronly) as Switch
        val descriptionEditText = findViewById(R.id.edit_description) as EditText

        val startDateButton = findViewById(R.id.button_eventdate_start_picker) as Button
        val startTimeButton = findViewById(R.id.button_eventtime_start_picker) as Button
        val endDateButton = findViewById(R.id.button_eventdate_end_picker) as Button
        val endTimeButton = findViewById(R.id.button_eventtime_end_picker) as Button
        val deadlineButton = findViewById(R.id.button_deadline_picker) as Button

        val eventImageButton = findViewById(R.id.imagebutton_eventImage) as ImageButton

        Picasso.with(applicationContext).load("https://mb.api.cloud.nifty.com/2013-09-01/applications/zUockxBwPHqxceBH/publicFiles/${event.id}.png").into(eventImageButton)

        val editButton = findViewById(R.id.button_eventcreate) as Button
        editButton.setText(R.string.event_edit_button_text)

        departmentSpinner.setSelection(spinnerIndex)
        titleEditText.setText(event.title)
        locationEditText.setText(event.location)
        capacityEditText.setText(event.capacity)
        descriptionEditText.setText(event.description)
        startDateButton.setText(event.date_start)
        startTimeButton.setText(event.time_start)
        endDateButton.setText(event.date_end)
        endTimeButton.setText(event.time_end)
        deadlineButton.setText(event.deadline)
        if(event.officer_only) officerOnlySwitch.toggle()

        officerOnlySwitch.setOnCheckedChangeListener( { buttonView, isChecked ->
            officer = isChecked
        })

        editButton.setOnClickListener{
            val title = titleEditText.text.toString()
            val department = departmentSpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            val start_date = startDateButton.text.toString()
            val start_time = startTimeButton.text.toString()
            val end_date = endDateButton.text.toString()
            val end_time = endTimeButton.text.toString()
            val capacity = capacityEditText.text.toString()
            val deadline = deadlineButton.text.toString()
            val officer_only = officer



            val event = Event(title, event.id ,department, start_date, start_time, end_date, end_time, description, location, capacity, deadline, "", officer_only)
            event.update(this)


            finish()
        }
    }

    fun setSpinnerSelection(spinnerAdapter: ArrayAdapter<String>): Int{
        var position = 0
        for(i in 0..spinnerAdapter.count){
            position = if(spinnerAdapter.getItem(i) == event.department) i else -1
            if(position >= 0) break
        }
        return position
    }
}