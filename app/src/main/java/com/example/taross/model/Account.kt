package com.example.taross.model

import android.os.Parcel
import android.os.Parcelable
import com.example.taross.jinkawa_android.PasswordChangeActivity
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.NCMBQuery

/**
 * Created by taross on 2017/10/30.
 */
data class Account(val userId:String, val password:String, val role:String , val auth:List<String>) {
//    companion object {
//        @JvmField
//        val CREATOR: Parcelable.Creator<Account> = object : Parcelable.Creator<Account>{
//            override fun createFromParcel(source: Parcel): Account = source.run {
//                Account(readString(),readString(),readString(),createStringArrayList())
//            }
//
//            override fun newArray(size: Int): Array<Account?> = arrayOfNulls(size)
//        }
//    }
//
//    init {
//
//    }
//
//
//    override fun describeContents(): Int = 0
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//
//        dest.run {
//            writeString(userId)
//            writeString(password)
//            writeString(role)
//            writeList(auth)
//        }
//    }
//
    fun updatePass(activity: PasswordChangeActivity) {
        val query: NCMBQuery<NCMBObject> = NCMBQuery("Accounts")
        query.whereEqualTo("userId", this.userId)
        val datas: List<NCMBObject> = try {
            query.find()
        } catch (e: Exception) {
            emptyList<NCMBObject>()
        }
        if (datas.isNotEmpty()) {
            val data = datas[0]
            data.put("password", this.password)
            try {
                data.save()
            } catch (e: Exception) {
                println("Password save error : " + e.cause.toString())
            }
        }
    }
}