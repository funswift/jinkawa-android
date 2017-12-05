package com.example.taross.model

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.nifty.cloud.mb.core.*
import com.example.taross.jinkawa_android.*

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

    fun delete(){
        val queryEvent: NCMBQuery<NCMBObject> = NCMBQuery("Event")
        queryEvent.whereEqualTo("objectId", this.id)
        val datas: List<NCMBObject> = try {
            queryEvent.find()
        } catch (e: Exception) {
            emptyList<NCMBObject>()
        }
        if (datas.isNotEmpty()) {
            val data = datas[0]
            try {
                data.deleteObject()
            } catch (e: Exception) {
                println("Event data delete error : " + e.cause.toString())
            }
        }
        val queryImage: NCMBQuery<NCMBFile> = NCMBFile.getQuery()
        queryImage.whereEqualTo("fileName", "${this.id}.png")
        val files: List<NCMBFile> = try{
            queryImage.find()
        } catch (e: Exception){
            emptyList<NCMBFile>()
        }
        if(files.isNotEmpty()){
            val file = files[0]
            try {
                file.delete()
            } catch (e: Exception){
                println("Event image delete error : " + e.cause.toString())
            }
        }
    }

    fun selectDepartmentColorId(resources: Resources): Int{
        val departmentStrings = resources.getStringArray(R.array.array_departments)
        val color = when(this.department){
            departmentStrings[0] -> R.color.yakuin
            departmentStrings[1] -> R.color.soumu
            departmentStrings[2] -> R.color.seishounen
            departmentStrings[3] -> R.color.josei
            departmentStrings[4] -> R.color.hukushi
            departmentStrings[5] -> R.color.kankyou
            departmentStrings[6] -> R.color.boukabouhan
            departmentStrings[7] -> R.color.koutsu
            departmentStrings[8] -> R.color.jbus
            else -> -1
        }
        Log.d("ColorID", "$color")
        return color
    }
}