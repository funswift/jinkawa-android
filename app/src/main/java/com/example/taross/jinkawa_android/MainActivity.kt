package com.example.taross.jinkawa_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle;
import android.widget.Button;
import com.nifty.cloud.mb.core.NCMB


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
            LoginManager.login()
            val intent = Intent(application, ListActivity::class.java)
            startActivity(intent)
        })
        NCMB.initialize(this.getApplicationContext(),"fe37c2186e22a438c980af699d831ac26d2ce6e05909c89e0677309528274a4d","9fd56b4ec717815b4d72081d9ae9e58192bdec9be30a416319d0069a6c33fd9f")
    }
}
