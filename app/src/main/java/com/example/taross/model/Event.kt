package com.example.taross.model

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat.startActivity
import com.example.taross.jinkawa_android.EventCreateActivity
import com.example.taross.jinkawa_android.ListActivity
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBObject

/**
 * Created by taross on 2017/08/12.
 */

data class Event(val title: String, val id:String, val department:String, val date_start:String, val time_start:String, val date_end:String, val time_end:String,  val location:String, val capacity:String, val deadline:String, val officer_only:Boolean): Parcelable{
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event>{
            override fun createFromParcel(source: Parcel): Event = source.run {
                Event(readString(),readString(),readString(),readString(),readString(),readString(),readString(),readString(),readString(), readString(), (readInt() == 1))
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
            writeString(location)
            writeString(capacity)
            writeString(deadline)
            writeInt(officer)
        }
    }

    fun save(activity :EventCreateActivity){
        val data = NCMBObject("Event")
        data.put("event_name", this.title)
        data.put("event_department_name",this.department)
        data.put("date_start", this.date_start)
        data.put("time_start", this.time_start)
        data.put("date_end", this.date_end)
        data.put("time_end", this.time_end)
        data.put("location", this.location)
        data.put("capacity", this.capacity)
        data.put("deadline_day", this.deadline)
        data.put("officer_only", this.officer_only)
        data.saveInBackground(activity as DoneCallback)
    }
}