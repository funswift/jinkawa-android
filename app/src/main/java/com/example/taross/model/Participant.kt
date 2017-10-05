package com.example.taross.model

import android.os.Parcel
import android.os.Parcelable
import android.provider.Telephony
import com.nifty.cloud.mb.core.NCMB
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBObject

/**
 * Created by taross on 2017/08/26.
 */
 data class Participant(var name:String = "", var age:String = "", val tel:String = "", val address:String = "", val gender:String = "", val eventId: String = ""):Parcelable{

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Participant> = object : Parcelable.Creator<Participant>{
            override fun createFromParcel(source: Parcel): Participant = source.run {
                    Participant(readString(),readString(),readString(),readString(),readString())
                }


            override fun newArray(size: Int): Array<Participant?> = arrayOfNulls(size)
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int = 0

    fun save(eventId :String){
        val data = NCMBObject("Participants")
        data.put("name", this.name)
        data.put("sex",this.gender)
        data.put("age", this.age)
        data.put("tell", this.tel)
        data.put("address", this.address)
        data.put("eventID", eventId )

        data.save()
    }
}