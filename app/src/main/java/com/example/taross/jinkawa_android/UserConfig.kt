package com.example.taross.jinkawa_android

import com.nifty.cloud.mb.core.NCMBInstallation
import org.json.JSONArray

/**
 * Created by taross on 2017/11/21.
 */

object UserConfig{
    var is_pushed = true
    // プッシュ通知はNCMBのチャンネルで制御、onチャンネルのみに配信する設定になっている
    fun sendPushConfigChange(){
        val installation = NCMBInstallation.getCurrentInstallation()
        installation.channels = if(is_pushed){
            JSONArray("[on]")
        } else{
            JSONArray("[off]")
        }
        installation.saveInBackground()
    }
}