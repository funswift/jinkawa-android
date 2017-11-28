package com.example.taross.jinkawa_android

import android.content.Context
import com.example.taross.model.Participant
import com.nifty.cloud.mb.core.NCMBInstallation
import org.json.JSONArray

/**
 * Created by taross on 2017/11/21.
 */

object UserConfig{
    private val CONFIG_FILE_NAME = "com.example.taross.jinkawa_android.user_config"

    private val KEY_IS_PUSHED = "pushed"
    private val KEY_PARTICIPANT_FLAG = "participant.flag"
    private val KEY_PARTICIPANT_NAME = "participant.name"
    private val KEY_PARTICIPANT_ADDRESS = "participant.address"
    private val KEY_PARTICIPANT_TELL = "participant.tell"
    private val KEY_PARTICIPANT_AGE = "participant.age"
    private val KEY_PARTICIPANT_GENDER = "participant.gender"

    // プッシュ通知はNCMBのチャンネルで制御、onチャンネルのみに配信する設定になっている
    fun sendPushConfigChange(context: Context, isChecked: Boolean){
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
                .putBoolean(KEY_IS_PUSHED, isChecked)
                .apply()

        val installation = NCMBInstallation.getCurrentInstallation()
        installation.channels =
                if(getIsPushed(context)){
                    JSONArray("[on]")
                } else{
                    JSONArray("[off]")
                }
        installation.saveInBackground()
    }

    fun setParticipantFlag(context: Context, flag: Boolean){
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
                .putBoolean(KEY_PARTICIPANT_FLAG, flag)
                .apply()
    }

    fun setParticipantData(context: Context, participant: Participant){
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
                .putString(KEY_PARTICIPANT_NAME, participant.name)
                .putString(KEY_PARTICIPANT_ADDRESS, participant.address)
                .putString(KEY_PARTICIPANT_TELL, participant.tell)
                .putString(KEY_PARTICIPANT_AGE, participant.age)
                .putString(KEY_PARTICIPANT_GENDER, participant.gender)
                .apply()
    }

    fun getIsPushed(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_PUSHED, true)
    }

    fun getParticipantFlag(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_PARTICIPANT_FLAG, false)
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

    fun resetParticipantData(context: Context){
        val sharedPreferences = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)

        sharedPreferences.edit()
                .putBoolean(KEY_PARTICIPANT_FLAG, false)
                .remove(KEY_PARTICIPANT_NAME)
                .remove(KEY_PARTICIPANT_ADDRESS)
                .remove(KEY_PARTICIPANT_TELL)
                .remove(KEY_PARTICIPANT_AGE)
                .remove(KEY_PARTICIPANT_GENDER)
                .apply()
    }
}