package com.example.taross.jinkawa_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.model.Notice

/**
 * Created by y_snkw on 2017/11/14.
 */
class NoticeDetailActivity : AppCompatActivity() {

    val notice : Notice by lazy { intent.getParcelableExtra<Notice>(ITEM_EXTRA) }
    companion object {
        private const val ITEM_EXTRA = "item"

        fun intent(context: Context, item: Notice):Intent =
                Intent(context,NoticeDetailActivity::class.java).putExtra(ITEM_EXTRA, item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        val toolBar =findViewById(R.id.detail_toolbar) as Toolbar
        toolBar.title = notice.title

        if(LoginManager.isLogin){
            setSupportActionBar(toolBar)
        }

        val imageView = findViewById(R.id.imageView) as ImageView
        imageView.setImageResource(R.drawable.ic_notice_info)

        val departmentTextView = findViewById(R.id.detail_department_name) as TextView
        departmentTextView.text = notice.department

        val dateTextView = findViewById(R.id.detail_date) as TextView
        dateTextView.text = notice.date

        val descriptionTextView = findViewById(R.id.detail_description) as TextView
        descriptionTextView.text = notice.description
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (LoginManager.isLogin)
            LoginManager.account?.let {
                if (it.auth.any{ it == "all" || it == notice.department})
                    menuInflater.inflate(R.menu.menu_notice_list, menu)
            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if(id == R.id.action_notice_edit){
            startActivity(Intent(applicationContext, NoticeEditActivity::class.java).putExtra("NOTICE_EXTRA", notice))
            return true
        }else if(id == R.id.action_notice_delete){
            notice.delete()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}