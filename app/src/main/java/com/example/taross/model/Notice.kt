package com.example.taross.model

import android.os.Parcel
import android.os.Parcelable
import com.example.taross.jinkawa_android.NoticeCreateActivity
import com.example.taross.jinkawa_android.NoticeEditActivity
import com.nifty.cloud.mb.core.DoneCallback
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.NCMBQuery

/**
 * Created by taross on 2017/08/14.
 */

data class Notice(val title:String, val id:String, val department:String, val date:String, val description:String, val update_date:String, val officer_only:Boolean):Parcelable{
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notice> = object : Parcelable.Creator<Notice>{
            override fun newArray(size: Int): Array<Notice?> = arrayOfNulls(size)

            override fun createFromParcel(source: Parcel): Notice = source.run {
                Notice(readString(),readString(),readString(),readString(),readString(),readString(),readInt() == 1)
            }
        }
    }

    init {

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        val officer = if(officer_only == true) 1 else 0

        dest.run {
            writeString(title)
            writeString(id)
            writeString(department)
            writeString(date)
            writeString(description)
            writeString(update_date)
            writeInt(officer)
        }
    }

    override fun describeContents(): Int = 0

    fun save(activity : NoticeCreateActivity){
        val data = NCMBObject("Information")
        data.put("title", this.title)
        data.put("department_name",this.department)
        data.put("date", this.date)
        data.put("info", this.description)
        data.put("officer_only", this.officer_only)
        data.saveInBackground(activity as DoneCallback)
    }

    fun update(activity: NoticeEditActivity) {
        val query: NCMBQuery<NCMBObject> = NCMBQuery("Information")
        query.whereEqualTo("objectId", this.id)
        val datas: List<NCMBObject> = try {
            query.find()
        } catch (e: Exception) {
            emptyList<NCMBObject>()
        }
        if (datas.isNotEmpty()) {
            val data = datas[0]
            data.put("title", this.title)
            data.put("department_name", this.department)
            data.put("date", this.date)
            data.put("info", this.description)
            data.put("officer_only", this.officer_only)

            try {
                data.save()
            } catch (e: Exception) {
                println("Notice data save error : " + e.cause.toString())
            }
        }
    }

}