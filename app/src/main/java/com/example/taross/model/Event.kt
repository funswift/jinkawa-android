package com.example.taross.model

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.example.taross.jinkawa_android.EventCreateActivity
import com.example.taross.jinkawa_android.EventEditActivity
import com.example.taross.jinkawa_android.ListActivity
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.NCMBQuery

/**
 * Created by taross on 2017/08/12.
 */

data class Event(val title: String, val id:String, val department:String, val date_start:String, val time_start:String, val date_end:String, val time_end:String,  val description:String,val location:String, val capacity:String, val deadline:String, val update_date:String, val officer_only:Boolean): Parcelable{
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event>{
            override fun createFromParcel(source: Parcel): Event = source.run {
                Event(readString(),readString(),readString(),readString(),readString(),readString(),readString(),readString(),readString(), readString(),readString() ,readString(), readInt() == 1)
            }

            override fun newArray(size: Int): Array<Event?> = arrayOfNulls(size)
        }
    }

    init {

    }


    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        val officer = if(officer_only == true) 1 else 0

        dest.run {
            writeString(title)
            writeString(id)
            writeString(department)
            writeString(date_start)
            writeString(time_start)
            writeString(date_end)
            writeString(time_end)
            writeString(description)
            writeString(location)
            writeString(capacity)
            writeString(deadline)
            writeString(update_date)
            writeInt(officer)
        }
    }

    fun save(activity :EventCreateActivity){
        val data = NCMBObject("Event")
        data.put("name", this.title)
        data.put("department",this.department)
        data.put("date_start", this.date_start)
        data.put("start_time", this.time_start)
        data.put("date_end", this.date_end)
        data.put("end_time", this.time_end)
        data.put("description", this.description)
        data.put("location", this.location)
        data.put("capacity", this.capacity)
        data.put("deadline_day", this.deadline)
        data.put("officer_only", this.officer_only)
        data.saveInBackground(activity as DoneCallback)
    }

    fun update(activity: EventEditActivity) {
        val query: NCMBQuery<NCMBObject> = NCMBQuery("Event")
        query.whereEqualTo("objectId", this.id)
        val datas: List<NCMBObject> = try {
            query.find()
        } catch (e: Exception) {
            emptyList<NCMBObject>()
        }
        if (datas.isNotEmpty()) {
            val data = datas[0]
            data.put("name", this.title)
            data.put("department", this.department)
            data.put("date_start", this.date_start)
            data.put("start_time", this.time_start)
            data.put("date_end", this.date_end)
            data.put("end_time", this.time_end)
            data.put("description", this.description)
            data.put("location", this.location)
            data.put("capacity", this.capacity)
            data.put("deadline_day", this.deadline)
            data.put("officer_only", this.officer_only)

            try {
                data.save()
            } catch (e: Exception) {
                println("Event data save error : " + e.cause.toString())
            }
        }
    }
}