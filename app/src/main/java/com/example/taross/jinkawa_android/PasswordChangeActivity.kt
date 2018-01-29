package com.example.taross.jinkawa_android

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.*
import com.example.taross.model.Account

/**
 * Created by y_snkw on 2017/11/19.
 */
class PasswordChangeActivity : AppCompatActivity() {

//    //DoneCallBack インターフェースの実装
//    override fun done(arg1: NCMBException?) {
//        val intent = Intent(applicationContext, EventDetailActivity::class.java)
//        startActivity(intent)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change)

        val toolbar = findViewById(R.id.toolbar_pass_change) as Toolbar
        toolbar.title = getString(R.string.title_pass_change)

        val idTextView = findViewById(R.id.pass_id_text) as TextView
        idTextView.text = LoginManager.account?.userId

        val oldInputLayout = findViewById(R.id.textInput_oldpass) as TextInputLayout
        val confInputLayout = findViewById(R.id.textInput_confpass) as TextInputLayout
        val oldPassEditView = findViewById(R.id.edit_oldpass) as EditText
        val newPassEditView = findViewById(R.id.edit_newpass) as EditText
        val confPassEditView = findViewById(R.id.edit_confpass) as EditText
        val passChangeButton = findViewById(R.id.button_pass_change) as Button

        passChangeButton.setOnClickListener {
            val id = LoginManager.account?.userId
            val nowPass = LoginManager.account?.password
            val inOldPass = oldPassEditView.text.toString()
            val inNewPass = newPassEditView.text.toString()
            val inConfPass = confPassEditView.text.toString()

            //エラー処理の分岐もあとで要修正
            val success: Boolean = when{
                nowPass != inOldPass -> {
                    //エラー処理を追加する
                    Log.d("Error", "not nowPass")
                    alert(getString(R.string.pass_change_error_1)){
                        title = getString(R.string.error_text)
                        positiveButton(getString(R.string._return)) {  }
                    }.show()
                    false
                }
                inNewPass == "" -> {
                    //エラー処理を追加する
                    Log.d("Error", "newPass is Empty")
                    alert(getString(R.string.pass_change_error_2)){
                        title = getString(R.string.error_text)
                        positiveButton(getString(R.string._return)) {  }
                    }.show()
                    false
                }
                inNewPass != inConfPass -> {
                    //エラー処理を追加する
                    Log.d("Error", "confPass is not newPass")
                    alert(getString(R.string.pass_change_error_3)){
                        title = getString(R.string.error_text)
                        positiveButton(getString(R.string._return)) {  }
                    }.show()
                    false
                }
                else -> {
                    val account = Account(id!!, inNewPass, LoginManager.account!!.role, LoginManager.account!!.auth)
                    account.updatePass(this)
                    true
                }
            }

            if(success) {
                LoginManager.logout()
                startActivity(Intent(application, MainActivity::class.java))
            }

        }
    }
}