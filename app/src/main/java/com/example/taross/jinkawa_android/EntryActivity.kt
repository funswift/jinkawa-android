package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.taross.model.Participant

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val toolbar = findViewById(R.id.toolbar_event_entry) as Toolbar
        toolbar.title = getString(R.string.title_event_entry)

        var participant = Participant()
        val nameText = findViewById(R.id.text_name) as EditText
        val addressText = findViewById(R.id.text_address) as EditText
        val tellText = findViewById(R.id.text_tell) as EditText
        val ageText = findViewById(R.id.text_age) as EditText

        val genderRadioGroup = findViewById(R.id.radioGroup) as RadioGroup

        if(UserConfig.getParticipantFlag(applicationContext)) {
            participant = UserConfig.getParticipantData(applicationContext)
            nameText.setText(participant.name)
            addressText.setText(participant.address)
            tellText.setText(participant.tell)
            ageText.setText(participant.age)
            genderRadioGroup.check(if(participant.gender == "男") R.id.button_gender_man else R.id.button_gender_woman)
        }

        genderRadioGroup.setOnCheckedChangeListener{_, isChecked ->
            participant.gender = when(isChecked){
                R.id.button_gender_man -> "男"
                else ->  "女"
            }
        }

        val eventId = intent.getStringExtra("EVENT_ID_EXTRA")
        val submitButton = findViewById(R.id.button_entry_submit) as Button
        submitButton.setOnClickListener({
            participant.name = nameText.text.toString()
            participant.address = addressText.text.toString()
            participant.tell = tellText.text.toString()
            participant.age = ageText.text.toString()

            UserConfig.setParticipantFlag(applicationContext, true)
            UserConfig.setParticipantData(applicationContext, participant)
            participant.save(eventId)
            finish()
        })
    }
}
