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