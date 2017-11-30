package com.example.taross.jinkawa_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.*
import com.example.taross.model.Notice
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.SearchPushCallback

open class NoticeCreateActivity : AppCompatActivity(), DoneCallback {

    //DoneCallBack インターフェースの実装
    override fun done(arg1: NCMBException?){
        val intent = Intent(applicationContext,  ListActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.activity_notice_create)

        val toolbar = findViewById(R.id.toolbar_notice_create) as Toolbar
        toolbar.title = getString(R.string.title_notice_create)

        val departmentSpinner : Spinner = findViewById(R.id.spinner_department) as Spinner

        var personalAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item)
        LoginManager.account?.let {
            personalAdapter =
                    if (it.auth.any{it == "all"})
                        ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_departments))
                    else
                        ArrayAdapter(applicationContext, R.layout.spinner_item, it.auth)
        }
        departmentSpinner.adapter = personalAdapter

        val typeSpinner : Spinner = findViewById(R.id.spinner_type) as Spinner
        val typePersonalAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_notice_types))
        typeSpinner.adapter = typePersonalAdapter

        var officer = false

        val titleEditText = findViewById(R.id.edit_notice_title) as EditText
        val descriptionEditText = findViewById(R.id.edit_notice_description) as EditText
        val officerOnlySwitch = findViewById(R.id.switch_officeronly) as Switch

        val createButton = findViewById(R.id.button_notice_create) as Button

        officerOnlySwitch.setOnCheckedChangeListener( { buttonView, isChecked ->
            officer = isChecked
        })

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val department = departmentSpinner.selectedItem.toString()
            val type = typeSpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()
            val officer_only = officer

            if(true){

            }

            val notice = Notice(title, "", department, "20XX/YY/ZZ HH:MM", description, "", type, officer_only)
            notice.save(this)
            NotificationHelper.sendPush(title, "お知らせが追加されました！")
        }
    }
}
