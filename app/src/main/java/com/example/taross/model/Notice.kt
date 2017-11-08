package com.example.taross.model

import android.os.Parcel
import android.os.Parcelable
import com.example.taross.jinkawa_android.NoticeCreateActivity
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBObject

/**
 * Created by taross on 2017/08/14.
 */

data class Notice(val title:String, val department:String, val date:String, val description:String, val update_date:String):Parcelable{
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notice> = object : Parcelable.Creator<Notice>{
            override fun newArray(size: Int): Array<Notice?> = arrayOfNulls(size)

            override fun createFromParcel(source: Parcel): Notice = source.run {
                Notice(readString(),readString(),readString(),readString(),readString())
            }
        }
    }

    init {

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeString(title)
            writeString(department)
            writeString(date)
            writeString(description)
            writeString(update_date)
        }
    }

    override fun describeContents(): Int = 0

    fun save(activity : NoticeCreateActivity){
        val data = NCMBObject("Notice")
        data.put("event_name", this.title)
        data.put("event_department_name",this.department)
        data.put("date", this.date)
        data.put("description", this.description)

        data.saveInBackground(activity as DoneCallback)
    }

}