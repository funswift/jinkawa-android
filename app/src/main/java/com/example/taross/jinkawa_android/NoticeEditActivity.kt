package com.example.taross.jinkawa_android

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import android.widget.*
import com.example.taross.model.Notice
import com.nifty.cloud.mb.core.NCMBException
import org.jetbrains.anko.*


/**
 * Created by y_snkw on 2017/11/15.
 */
class NoticeEditActivity: NoticeCreateActivity() {

    val notice : Notice by lazy{intent.getParcelableExtra<Notice>("NOTICE_EXTRA")}

    //DoneCallBack インターフェースの実装
    override fun done(arg1: NCMBException?) {
        val intent = Intent(applicationContext, NoticeDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar_notice_create) as Toolbar
        toolbar.title = getString(R.string.title_notice_edit)

        val departmentSpinner : Spinner = findViewById(R.id.spinner_department) as Spinner
        var departmentPersonalAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item)
        LoginManager.account?.let {
            departmentPersonalAdapter=
                    if (it.auth.any{it == "all"})
                        ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_departments))
                    else
                        ArrayAdapter(applicationContext, R.layout.spinner_item, it.auth)
        }
        departmentSpinner.adapter = departmentPersonalAdapter

        val typeSpinner : Spinner = findViewById(R.id.spinner_type) as Spinner
        val typePersonalAdapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.spinner_item, resources.getStringArray(R.array.array_notice_types))
        typeSpinner.adapter = typePersonalAdapter

        val departmentSpinnerIndex = setSpinnerSelection(departmentPersonalAdapter, notice.department)
        val typeSpinnerIndex = setSpinnerSelection(typePersonalAdapter, notice.type)
        var officer = false

        val titleEditText = findViewById(R.id.edit_notice_title) as EditText
        val descriptionEditText = findViewById(R.id.edit_notice_description) as EditText
        val officerOnlySwitch = findViewById(R.id.switch_officeronly) as Switch

        val editButton = findViewById(R.id.button_notice_create) as Button
        editButton.setText(R.string.notice_edit_button_text)

        departmentSpinner.setSelection(departmentSpinnerIndex)
        typeSpinner.setSelection(typeSpinnerIndex)
        titleEditText.setText(notice.title)
        descriptionEditText.setText(notice.description)
        if(notice.officer_only) officerOnlySwitch.toggle()

        officerOnlySwitch.setOnCheckedChangeListener( { buttonView, isChecked ->
            officer = isChecked
        })

        editButton.setOnClickListener{
            val _title = titleEditText.text.toString()
            val department = departmentSpinner.selectedItem.toString()
            val type = typeSpinner.selectedItem.toString()
            val description = descriptionEditText.text.toString()
            val officer_only = officer

            val open_text: String? = when(officer_only){
                true -> getString(R.string.form_officer_text)
                false -> getString(R.string.form_officer_false_text)
            }

            val notice = Notice(_title, notice.id, department, notice.date, description, "", type, officer_only)
            alert(R.string.create_confirm){
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
                            textView(getString(R.string.form_notice_type_text) + colon) { textSize = _textSize }.lparams{ topMargin = dip(8) }
                            textView(type) { textSize = _textSize }.lparams{ topMargin = dip(8) }
                        }
                        linearLayout {
                            textView(getString(R.string.form_officer_description_text) + colon) { textSize = _textSize }.lparams { topMargin = dip(8) }
                            textView(open_text) { textSize = _textSize }.lparams { topMargin = dip(8) }
                        }
                    }
                }
                positiveButton(getString(R.string.yes)){
                    notice.update(this@NoticeEditActivity)
                    finish()
                }
                negativeButton(getString(R.string.no)){}
            }.show()
        }
    }


    fun setSpinnerSelection(spinnerAdapter: ArrayAdapter<String>, word: String): Int{
        var position = 0
        for(i in 0..spinnerAdapter.count){
            position = if(spinnerAdapter.getItem(i) == word) i else -1
            if(position >= 0) break
        }
        return position
    }
}