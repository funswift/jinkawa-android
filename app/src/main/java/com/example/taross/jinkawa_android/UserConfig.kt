package com.example.taross.jinkawa_android

import android.content.Context
import com.example.taross.model.Participant
import com.nifty.cloud.mb.core.NCMBInstallation
import org.json.JSONArray

/**
 * Created by taross on 2017/11/21.
 */

object UserConfig{
    val CONFIG_FILE_NAME = "com.example.taross.jinkawa_android.config"

    val KEY_IS_PUSHED = "pushed"
    val KEY_PARTICIPANT_FLAG = "participant.flag"
    private val KEY_PARTICIPANT_NAME = "participant.name"
    private val KEY_PARTICIPANT_ADDRESS = "participant.address"
    private val KEY_PARTICIPANT_TELL = "participant.tell"
    private val KEY_PARTICIPANT_AGE = "participant.age"
    private val KEY_PARTICIPANT_GENDER = "participant.gender"

    var is_pushed = true
    val channel = arrayOf("on", "off")

    fun sendPushConfigChange(context: Context){
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        val pushed = sharedPreferences.getBoolean(KEY_PARTICIPANT_FLAG, true)

        val installation = NCMBInstallation.getCurrentInstallation()
        installation.channels =
                if(is_pushed){
                    JSONArray("[on]")
                } else{
                    JSONArray("[off]")
                }
        installation.saveInBackground()
    }

    fun getParticipantData(context: Context): Participant{
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)

        val p_name = sharedPreferences.getString(KEY_PARTICIPANT_NAME, "Unknown")
        val p_address = sharedPreferences.getString(KEY_PARTICIPANT_ADDRESS, "Unknown")
        val p_tell = sharedPreferences.getString(KEY_PARTICIPANT_TELL, "Unknown")
        val p_age = sharedPreferences.getString(KEY_PARTICIPANT_AGE, "Unknown")
        val p_gender = sharedPreferences.getString(KEY_PARTICIPANT_GENDER, "Unknown")

        val participant = Participant(p_name, p_age, p_tell, p_address, p_gender, "")

        return participant
    }
}