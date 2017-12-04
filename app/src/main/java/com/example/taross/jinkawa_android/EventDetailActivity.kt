package com.example.taross.jinkawa_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.taross.model.Event
import com.squareup.picasso.Picasso

class EventDetailActivity : AppCompatActivity() {

    val event :Event by lazy { intent.getParcelableExtra<Event>(ITEM_EXTRA) }
    companion object {
        private const val ITEM_EXTRA = "item"

        fun intent(context: Context, item: Event):Intent =
                Intent(context,EventDetailActivity::class.java).putExtra(ITEM_EXTRA, item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val toolBar = findViewById(R.id.detail_toolbar) as Toolbar
        toolBar.title = event.title

        if(LoginManager.isLogin){
            setSupportActionBar(toolBar)
        }
        val imageButton = findViewById(R.id.imageButton_eventDetail) as ImageButton
        Picasso.with(applicationContext).load("https://mb.api.cloud.nifty.com/2013-09-01/applications/zUockxBwPHqxceBH/publicFiles/${event.id}.png").into(imageButton)
        imageButton.setOnClickListener({
            startActivity(Intent(applicationContext,EventDetailTranslucentActivity::class.java).putExtra("EVENT_EXTRA", event))
        })

        val departmentTextView = findViewById(R.id.detail_department) as TextView
        departmentTextView.text = event.department
        val idColor = event.selectDepartmentColorId(resources)
        if(idColor != -1)
            departmentTextView.setBackgroundResource(idColor)

        val updateTextView = findViewById(R.id.detail_last_update) as TextView
        updateTextView.text = event.update_date

        val startTextView = findViewById(R.id.detail_start) as TextView
        startTextView.text = event.date_start

        val endTextView = findViewById(R.id.detail_end) as TextView
        endTextView.text = event.date_end

        val locationTextView = findViewById(R.id.detail_location) as TextView
        locationTextView.text = event.location

        val capacityTextView = findViewById(R.id.detail_capacity) as TextView
        capacityTextView.text = event.capacity

        val deadlineTextView = findViewById(R.id.detail_capacity) as TextView
        deadlineTextView.text = event.deadline

        val descriptionTextView = findViewById(R.id.detail_description) as TextView
        descriptionTextView.text = event.description

        val entryButton = findViewById(R.id.button_entry) as Button
        entryButton.setOnClickListener({
            startActivity(Intent(applicationContext, EntryActivity::class.java).putExtra("EVENT_ID_EXTRA", event.id))
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (LoginManager.isLogin)
            LoginManager.account?.let {
                if (it.auth.any{ it == "all" || it == event.department})
                menuInflater.inflate(R.menu.menu_event_list, menu)
            }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_list) {
            startActivity(Intent(applicationContext,ParticipantsListActivity::class.java).putExtra("EVENT_EXTRA", event))
            return true
        }else if(id == R.id.action_event_edit){
            startActivity(Intent(applicationContext,EventEditActivity::class.java).putExtra("EVENT_EXTRA", event))
            return true
        }else if (id == R.id.action_event_delete){
            event.delete()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
