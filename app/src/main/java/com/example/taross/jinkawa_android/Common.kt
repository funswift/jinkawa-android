package com.example.taross.jinkawa_android

import com.example.taross.model.Event
import com.example.taross.model.Participant
import java.io.File
import java.io.Console
import android.R.attr.data
import android.content.Context
import com.nifty.cloud.mb.core.*
import org.json.JSONArray
import java.nio.charset.Charset
import org.json.JSONException


/**
 * Created by taross on 2017/09/09.
 */
class CsvHelper{
    companion object {
        fun csvListOutput(event:Event, participants:MutableList<Participant>, callback:(()->Unit)){
            
            var data = event.title + "," + event.date_start + "," + event.location + "," + event.department
            data += "\r\n"
            data += " "
            data += "\r\n"
            data += "氏名,年齢,性別,電話番号,住所"
            data += "\r\n"

            participants.forEach {
                var sep = ""
                data += sep + it.name
                sep = ","
                data += sep + it.age
                data += sep + it.gender
                data += sep + it.tel
                data += sep + it.address
                data += "\r\n"
            }

            val file = NCMBFile("${event.id}.csv", data.toByteArray(Charsets.UTF_16), NCMBAcl())
            file.saveInBackground {
                callback()
            }
        }
    }
}

class NotificationHelper{

    companion object {
        @Throws(JSONException::class)
        fun sendPush(title:String, message:String) {
            val push = NCMBPush()
            val query:NCMBQuery<NCMBInstallation> = NCMBQuery("Installation")
            push.action = "com.sample.pushsample.RECEIVE_PUSH"
            push.title = title
            push.message = message
            push.dialog = true
            push.target = JSONArray("[ios, android]")
            query.whereEqualTo("channels", "on")
            push.setSearchCondition(query)
            push.sendInBackground { e ->
                if (e != null) {
                    // エラー処理
                } else {
                    // プッシュ通知登録後の操作
                }
            }
        }

    }
}