package com.example.taross.jinkawa_android

import android.content.Context
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


/**
 * Created by taross on 2017/08/12.
 */

class EventListAdapter(private val context: Context): BaseAdapter(){
    var items: MutableList<Event> = eventLoad()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = ((convertView as? EventItemView) ?: EventItemView(context)).apply {
        setItem(items[position])
    }

    fun eventLoad():MutableList<Event>{
        val eveltList:MutableList<Event> = mutableListOf<Event>()

        val query:NCMBQuery<NCMBObject> = NCMBQuery("Event")
        val results: List<NCMBObject> = try {
            query.find()
        } catch (e : Exception) { emptyList<NCMBObject>() }
        if (results.isNotEmpty()) {
            for(result in results){
                val event: Event = Event(
                        result.getString("event_name"),
                        result.getString("objectId"),
                        result.getString("event_department_name"),
                        result.getString("day"),
                        result.getString("location"),
                        result.getString("capacity")
                )
                eveltList.add(event)
            }
        }
        return eveltList
    }
}