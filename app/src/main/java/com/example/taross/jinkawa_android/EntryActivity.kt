package com.example.taross.jinkawa_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import com.example.taross.model.Event
import com.example.taross.model.Participant
import org.w3c.dom.Text

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val toolbar = findViewById(R.id.toolbar_event_entry) as Toolbar
        toolbar.title = getString(R.string.title_event_entry)

        var participant = Participant()
        val nameText = findViewById(R.id.text_name) as EditText
        val ageText = findViewById(R.id.text_age) as EditText

        participant.name = nameText.text.toString()
        participant.age = ageText.text.toString()

        val eventId = intent.getStringExtra("EVENT_ID_EXTRA")
        val submitButton = findViewById(R.id.button_entry_submit) as Button
        submitButton.setOnClickListener({
            participant.save(eventId)
        })
    }
}
