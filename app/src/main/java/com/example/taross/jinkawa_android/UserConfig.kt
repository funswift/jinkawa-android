package com.example.taross.jinkawa_android

import com.nifty.cloud.mb.core.NCMBInstallation
import org.json.JSONArray

/**
 * Created by taross on 2017/11/21.
 */

object UserConfig{
    val CONFIG_FILE_NAME = "com.example.taross.jinkawa_android.config"

    val KEY_IS_PUSHED = "pushed"
    val KEY_PARTICIPANT_NAME = "participant.name"
    val KEY_PARTICIPANT_ADDRESS = "participant.address"
    val KEY_PARTICIPANT_TELL = "participant.tell"
    val KEY_PARTICIPANT_AGE = "participant.age"
    val KEY_PARTICIPANT_SEX = "participant.sex"

    var is_pushed = true
    val channel = arrayOf("on", "off")

    fun sendPushConfigChange(){
        val installation = NCMBInstallation.getCurrentInstallation()
        installation.channels =
                if(is_pushed){
                    JSONArray("[on]")
                } else{
                    JSONArray("[off]")
                }
        installation.saveInBackground()
    }
}