package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.taross.model.Participant
import org.jetbrains.anko.*

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
        participant.gender = "男"

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
        submitButton.setOnClickListener{
            participant.name = nameText.text.toString()
            participant.address = addressText.text.toString()
            participant.tell = tellText.text.toString()
            participant.age = ageText.text.toString()

            alert(getString(R.string.create_confirm)) {
                title = getString(R.string.confirm)
                val _textSize = 16f
                val colon = "："
                customView{
                    verticalLayout {
                        padding = dip(16)
                        linearLayout {
                            textView(getString(R.string.entry_item_name) + colon) {textSize = _textSize}
                            textView(participant.name) {textSize = _textSize}
                        }
                        linearLayout {
                            textView(getString(R.string.entry_item_address) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(participant.address) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.entry_item_tell) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(participant.tell) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.entry_item_age) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(participant.age) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.entry_item_gender) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(participant.gender) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                    }
                }
                positiveButton(getString(R.string.yes)){
                    UserConfig.setParticipantFlag(applicationContext, true)
                    UserConfig.setParticipantData(applicationContext, participant)
                    participant.save(eventId)
                    finish()
                }
                negativeButton(getString(R.string.no)){}
            }.show()
        }
    }
}
