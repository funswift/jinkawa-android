package com.example.taross.jinkawa_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle;
import android.widget.Button;
import com.nifty.cloud.mb.core.*
import org.json.JSONArray
import org.json.JSONException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sendButton = findViewById(R.id.button) as Button
        sendButton.setOnClickListener({
            LoginManager.logout()
            val intent = Intent(application, ListActivity::class.java)
            startActivity(intent)
        })

        val loginButton = findViewById(R.id.button2) as Button
        loginButton.setOnClickListener({

            val intent = Intent(application, LoginActivity::class.java)
            startActivity(intent)
        })

        val installation = NCMBInstallation.getCurrentInstallation()
//GCMからRegistrationIdを取得
        installation.getRegistrationIdInBackground("251945308702") { e ->
            if (e == null) {
                //ID取得成功
                try {
                    //mBaaSに端末情報を保存
                    installation.save()
                } catch (saveError: NCMBException) {
                    //保存失敗
                }

            } else {
                //ID取得失敗
            }
        }

        NCMB.initialize(this.getApplicationContext(),"fe37c2186e22a438c980af699d831ac26d2ce6e05909c89e0677309528274a4d","9fd56b4ec717815b4d72081d9ae9e58192bdec9be30a416319d0069a6c33fd9f")
    }
}
