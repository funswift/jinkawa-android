package com.example.taross.jinkawa_android

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.text.Editable
import android.util.Log
import android.view.WindowManager
import android.widget.*
import java.util.Calendar
import android.widget.ArrayAdapter
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.alert
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor


open class EventCreateActivity : AppCompatActivity(), DoneCallback {

    lateinit var eventImageButton: ImageButton
    var imageBmp: Bitmap? = null

    //DoneCallBack インターフェースの実装
    override fun done(arg1: NCMBException?){
        val intent = Intent(applicationContext,  ListActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 6542) {
            if (resultCode != Activity.RESULT_OK) {
                return
            }
            val imageUrl = data?.data ?: return
            val parcelFileDesc: ParcelFileDescriptor? = contentResolver.openFileDescriptor(imageUrl, "r")
            parcelFileDesc?.let {
                val fDesc: FileDescriptor = it.fileDescriptor
                val bmp = BitmapFactory.decodeFileDescriptor(fDesc)
                it.close()
                eventImageButton.setImageBitmap(bmp)
                imageBmp = bmp
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContentView(R.layout.activity_event_create)

        val toolbar = findViewById(R.id.toolbar_event_create) as Toolbar
        toolbar.title = getString(R.string.title_event_create)

        val departmentSpinner: Spinner = findViewById(R.id.spinner_department) as Spinner
        var personalAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item)

        LoginManager.account?.let {
            personalAdapter =
                    if (it.auth.any{it == "all"})
                        ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_departments))
                    else
                        ArrayAdapter(applicationContext, R.layout.spinner_item, it.auth)
        }

        departmentSpinner.adapter = personalAdapter

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
        val createButton = findViewById(R.id.button_eventcreate) as Button

        eventImageButton = findViewById(R.id.imagebutton_eventImage) as ImageButton

        eventImageButton.setOnClickListener{
            val intent= Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, 6542)
            Toast.makeText(this, "Open Garally", Toast.LENGTH_LONG).show()
        }

        startDateButton.setOnClickListener {
            val date = Calendar.getInstance()
            var dialog = DatePickerDialog(this, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (!view.isShown) {
                    return@OnDateSetListener
                }
                Log.d("hoge", "onDateSet")
                val fixed_month = monthOfYear + 1
                startDateButton.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        startTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                startTimeButton.text = "$hourOfDay:$minute"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
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
                endDateButton.text = "$year/$fixed_month/$dayOfMonth"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        endTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                endTimeButton.setText( "$hourOfDay:$minute")
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
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
                deadlineButton.setText("$year/$fixed_month/$dayOfMonth")
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }

        createButton.setOnClickListener{
            val _title = titleEditText.text.toString()
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

            officerOnlySwitch.setOnCheckedChangeListener( { buttonView, isChecked ->
                officer = isChecked
            })

            var confirm_ok = false
            val open_text: String? = when(officer_only){
                true -> getString(R.string.form_officer_text)
                false -> getString(R.string.form_officer_false_text)
            }

            val event = Event(_title, "", department, start_date, start_time, end_date, end_time, description, location, capacity, deadline, "", officer_only)

            alert(getString(R.string.create_confirm)) {
                title = getString(R.string.confirm)
                val _textSize = 16f
                val colon = "："
                customView {
                    verticalLayout {
                        padding = dip(16)
                        linearLayout {
                            textView(getString(R.string.form_department_text) + colon) { textSize = _textSize }
                            textView(department) { textSize = _textSize }
                        }
                        linearLayout {
                            textView(getString(R.string.form_event_title_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(_title) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_location_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(location) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_capacity_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(capacity + "人") { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_date_start_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(start_date + "　" + start_time) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_date_end_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(end_date + "　" + end_time) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_deadline_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(deadline) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_officer_false_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(open_text) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                    }
                }
                positiveButton(getString(R.string.create_text)){confirm_ok = true}
                negativeButton(getString(R.string._return)){}
            }.show()

            if(confirm_ok) {
                event.save(this)
                NotificationHelper.sendPush(_title, "イベントが追加されました！")


                //画像追加
                val query: NCMBQuery<NCMBObject> = NCMBQuery("Event")

                query.whereEqualTo("name", _title)
                val result = try {
                    query.find()
                } catch (e: Exception) {
                    emptyList<NCMBObject>()
                }
                Log.d("result", "$result")
                if (result.isNotEmpty()) {
                    Log.d("test", "${result[0].getString("objectId")}を取得しました！")
                    val fileName = result[0].getString("objectId")
                    imageBmp?.let {
                        val file = NCMBFile("${fileName}.png", getBitmapAsByteArray(it), NCMBAcl())
                        file.save()
                    }
                }
            }
        }
    }

    fun getBitmapAsByteArray(bitmap: Bitmap):ByteArray{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return  byteArrayOutputStream.toByteArray()
    }

}

