package com.example.taross.jinkawa_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.support.v7.widget.Toolbar
import com.example.taross.jinkawa_android.CsvHelper
import com.example.taross.model.Event

class ParticipantsListActivity : AppCompatActivity() {
    var event:Event? = null
    lateinit var adapter:ParticipantsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants_list)

        val toolBar = findViewById(R.id.toolbar3) as Toolbar
        toolBar.title = "参加者リスト"

        setSupportActionBar(toolBar)

        val listView = findViewById(R.id.participant_list) as ListView
        adapter = ParticipantsListAdapter(applicationContext)
        event = intent.getParcelableExtra("EVENT_EXTRA")
        event?.let {
            adapter?.filterParticipants(it.id)
            listView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_participant, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_save_csv) {
            event?.let {
                adapter.listExport(it)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
