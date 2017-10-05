package com.example.taross.jinkawa_android

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.taross.model.Participant
import com.example.taross.view.EventItemView
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.NCMBQuery
import android.content.Context
import com.example.taross.model.Event

import  com.example.taross.view.ParticipantItemView

/**
 * Created by taross on 2017/08/27.
 */

class ParticipantsListAdapter(private val context: Context):BaseAdapter(){
    var items: MutableList<Participant> = participantLoad()
    var eventId: String = ""

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = ((convertView as? ParticipantItemView) ?: ParticipantItemView(context)).apply {
        setItem(items[position])
    }

    fun participantLoad():MutableList<Participant>{
        val participantList:MutableList<Participant> = mutableListOf<Participant>()

        val query: NCMBQuery<NCMBObject> = NCMBQuery("Participants")
        val results: List<NCMBObject> = try {
            query.find()
        } catch (e : Exception) { emptyList<NCMBObject>() }
        if (results.isNotEmpty()) {
            for(result in results){
                val participant : Participant = Participant(
                        result.getString("name"),
                        result.getString("age"),
                        result.getString("tell"),
                        result.getString("address"),
                        result.getString("sex"),
                        result.getString("eventID")
                )

                participantList.add(participant)
            }
        }
        return participantList
    }

    fun filterParticipants(eventId: String) {
        this.items = items.filter { it.eventId == eventId }.toMutableList()
    }

    fun listExport(event: Event){
        CsvHelper.csvListOutput(event, items);
    }
}