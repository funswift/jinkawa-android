package com.example.taross.jinkawa_android

import com.example.taross.model.Event
import com.example.taross.model.Participant
import com.nifty.cloud.mb.core.NCMBAcl
import java.io.File
import com.nifty.cloud.mb.core.NCMBFile
import java.io.Console
import android.R.attr.data
import android.content.Context
import java.nio.charset.Charset
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.FetchFileCallback




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