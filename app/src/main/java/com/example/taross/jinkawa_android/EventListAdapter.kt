package com.example.taross.jinkawa_android

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.example.taross.model.Event
import com.example.taross.view.EventItemView
import com.nifty.cloud.mb.core.NCMB
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.FetchCallback
import com.nifty.cloud.mb.core.NCMBQuery
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import java.io.File




/**
 * Created by taross on 2017/08/12.
 */

class EventListAdapter(private val context: Context): LoadableListAdapter<Event>(){
    override var items: MutableList<Event> = loadData()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = ((convertView as? EventItemView) ?: EventItemView(context)).apply {
        setItem(items[position])

    }

    override fun loadData():MutableList<Event>{
        val eveltList:MutableList<Event> = mutableListOf<Event>()

        val query:NCMBQuery<NCMBObject> = NCMBQuery("Event")
        /*
            イベントを開催日順に並べる場合
            query.addOrderByDescending("date_start")
            query.addOrderByDescending("start_time")
        */
        query.addOrderByDescending("updateDate")
        val results: List<NCMBObject> = try {
            query.find()
        } catch (e : Exception) { emptyList<NCMBObject>() }
        if (results.isNotEmpty()) {
            for(result in results){
                val rs_updateDate = result.getString("updateDate")
                val mrs_updateDate = rs_updateDate.substring(0, 19).replace("T", " ")
                val event: Event = Event(
                        result.getString("name"),
                        result.getString("objectId"),
                        result.getString("department"),
                        result.getString("date_start"),
                        result.getString("start_time"),
                        result.getString("date_end"),
                        result.getString("end_time"),
                        result.getString("description"),
                        result.getString("location"),
                        result.getString("capacity"),
                        result.getString("deadline_day"),
                        mrs_updateDate,
                        result.getBoolean("officer_only")
                )
                eveltList.add(event)
            }
        }
        return eveltList
    }

    override fun reflesh() {
        //deleteDirectoryTree(context.cacheDir)
        this.items = loadData()
        this.filter()
    }

    fun deleteDirectoryTree(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) {
            for (child in fileOrDirectory.listFiles()!!) {
                deleteDirectoryTree(child)
            }
        }

        fileOrDirectory.delete()
    }

    override fun filter(){
        if(!LoginManager.isLogin)
            this.items = items.filter { it.officer_only == false }.toMutableList()
    }


}