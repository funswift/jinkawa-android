package com.example.taross.jinkawa_android

import com.example.taross.model.Event
import com.example.taross.model.Participant
import com.nifty.cloud.mb.core.NCMBAcl
import java.io.File
import com.nifty.cloud.mb.core.NCMBFile
import java.io.Console
import android.R.attr.data
import java.nio.charset.Charset


/**
 * Created by taross on 2017/09/09.
 */
class CsvHelper{
    companion object {
        public fun csvListOutput(event:Event, participants:MutableList<Participant>){
            
            var data = event.title + "," + event.date + "," + event.location + "," + event.department
            data += "\r\n"
            data += " "
            data += "\r\n"
            data += "氏名,年齢,性別,電話番号,住所"
            data += "\r\n"

            participants.forEach {
                var sep:String  = ""
                data += sep + it.name
                sep = ","
                data += sep + it.age
                data += sep + it.gender
                data += sep + it.tel
                data += sep + it.address
                data += "\r\n"
            }

            val file = NCMBFile("test.csv", data.toByteArray(Charsets.UTF_16), NCMBAcl())
            file.saveInBackground {
                print("upload")
            }
        }
    }
}