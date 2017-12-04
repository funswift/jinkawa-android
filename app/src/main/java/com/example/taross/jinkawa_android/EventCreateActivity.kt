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
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.util.Log
import android.view.WindowManager
import android.widget.*
import java.util.Calendar
import android.widget.ArrayAdapter
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.*
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor


open class EventCreateActivity : AppCompatActivity(), DoneCallback {

    lateinit var eventImageButton: ImageButton
    lateinit var toolbar:Toolbar

    lateinit var titleTextInputLayout:TextInputLayout
    lateinit var locationTextInputLayout:TextInputLayout
    lateinit var capacityTextInputLayout:TextInputLayout

    lateinit var titleEditText: EditText
    lateinit var locationEditText: EditText
    lateinit var capacityEditText: EditText
    lateinit var descriptionEditText: EditText

    lateinit var officerOnlySwitch: Switch

    lateinit var startDateButton: Button
    lateinit var startTimeButton: Button

    lateinit var endDateButton: Button
    lateinit var endTimeButton: Button

    lateinit var deadlineButton: Button
    lateinit var createButton: Button
    var imageBmp: Bitmap? = null

    lateinit var departmentSpinner: Spinner
    lateinit var personalAdapter: ArrayAdapter<String>

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

        toolbar = findViewById(R.id.toolbar_event_create) as Toolbar

        titleTextInputLayout = findViewById(R.id.textInput_eventtitle) as TextInputLayout
        locationTextInputLayout = findViewById(R.id.textInput_eventlocate) as TextInputLayout
        capacityTextInputLayout = findViewById(R.id.textInput_capacity) as TextInputLayout

        titleEditText = findViewById(R.id.edit_eventtitle) as EditText
        locationEditText = findViewById(R.id.edit_eventlocate) as EditText
        capacityEditText = findViewById(R.id.edit_capacity) as EditText
        officerOnlySwitch = findViewById(R.id.switch_officeronly) as Switch
        descriptionEditText = findViewById(R.id.edit_description) as EditText

        startDateButton = findViewById(R.id.button_eventdate_start_picker) as Button
        startTimeButton = findViewById(R.id.button_eventtime_start_picker) as Button

        endDateButton = findViewById(R.id.button_eventdate_end_picker) as Button
        endTimeButton = findViewById(R.id.button_eventtime_end_picker) as Button

        deadlineButton = findViewById(R.id.button_deadline_picker) as Button
        createButton = findViewById(R.id.button_eventcreate) as Button


        departmentSpinner = findViewById(R.id.spinner_department) as Spinner
        personalAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item)

        toolbar.title = getString(R.string.title_event_create)

        LoginManager.account?.let {
            personalAdapter =
                    if (it.auth.any{it == "all"})
                        ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_departments))
                    else
                        ArrayAdapter(applicationContext, R.layout.spinner_item, it.auth)
        }

        departmentSpinner.adapter = personalAdapter

        var officer = false

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

        officerOnlySwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            officer = isChecked
        }

        createButton.setOnClickListener{
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

            val event = Event(title, "", department, start_date, start_time, end_date, end_time, description, location, capacity, deadline, "", officer_only)

            //画像追加
            var query: NCMBQuery<NCMBObject>?
            var result:List<NCMBObject>?
            var fileName:String?
            var file:NCMBFile?

            if (validate()) {

                event.save(this)
                NotificationHelper.sendPush(title, "イベントが追加されました！")


                //画像追加
                query = NCMBQuery("Event")

                query.whereEqualTo("name", title)
                result = try {
                    query.find()
                } catch (e: Exception) {
                    emptyList<NCMBObject>()
                }
                Log.d("result", "$result")
                if (result.isNotEmpty()) {
                    Log.d("test", "${result[0].getString("objectId")}を取得しました！")
                    fileName = result[0].getString("objectId")
                    imageBmp?.let {
                        file = NCMBFile("${fileName}.png", getBitmapAsByteArray(it), NCMBAcl())
                        file?.save()
                    }
                }
            }
        }
    }

    fun validate():Boolean{
        var isSuccess = true

        if(titleEditText.text.length > 10){
            titleTextInputLayout.isErrorEnabled = true
            titleTextInputLayout.error = "入力文字数が10文字を超えています。"
            isSuccess = false
        } else if(titleEditText.text.length == 0){
            titleTextInputLayout.isErrorEnabled = true
            titleTextInputLayout.error = "必須項目です。"
            isSuccess = false
        }else {
            titleTextInputLayout.isErrorEnabled = false
        }

        if(locationEditText.text.length > 30){
            locationTextInputLayout.isErrorEnabled = true
            locationTextInputLayout.error = "入力文字数が30文字を超えています。"
            isSuccess = false
        } else if(locationEditText.text.length == 0){
            locationTextInputLayout.isErrorEnabled = true
            locationTextInputLayout.error = "必須項目です。"
            isSuccess = false
        } else {
            locationTextInputLayout.isErrorEnabled = false
        }

        if(capacityEditText.text.length > 5){
            capacityTextInputLayout.isErrorEnabled = true
            capacityTextInputLayout.error = "入力桁数が5桁を超えています。"
            isSuccess = false
        } else if(capacityEditText.text.length == 0){
            capacityEditText.setText("指定なし")
            capacityTextInputLayout.isErrorEnabled = false
        } else {
            capacityTextInputLayout.isErrorEnabled = false
        }

        if(startDateButton.text == "日付を選択"){
            Toast.makeText(applicationContext, "開始日が入力されていません", Toast.LENGTH_SHORT).show()
            isSuccess = false
        } else if (startTimeButton.text == "時間を選択"){
            Toast.makeText(applicationContext, "開始時間が入力されていません", Toast.LENGTH_SHORT).show()
            isSuccess = false
        } else if (endDateButton.text == "日付を選択"){
            Toast.makeText(applicationContext, "終了日が入力されていません", Toast.LENGTH_SHORT).show()
            isSuccess = false
        } else if (endTimeButton.text == "時間を選択"){
            Toast.makeText(applicationContext, "終了時間が入力されていません", Toast.LENGTH_SHORT).show()
            isSuccess = false
        } else if (deadlineButton.text == "日付を選択"){
            Toast.makeText(applicationContext, "申し込み締切日が入力されていません", Toast.LENGTH_SHORT).show()
            isSuccess = false
        }

        return isSuccess
    }

    fun getBitmapAsByteArray(bitmap: Bitmap):ByteArray{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return  byteArrayOutputStream.toByteArray()
    }
}

