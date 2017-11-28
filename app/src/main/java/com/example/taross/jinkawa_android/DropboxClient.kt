package com.example.taross.jinkawa_android

import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.DbxRequestConfig



/**
 * Created by taross on 2017/11/28.
 */
object DropboxClient {

    fun getClient(ACCESS_TOKEN: String): DbxClientV2 {
        // Create Dropbox client
        val config = DbxRequestConfig("dropbox/sample-app", "en_US")
        return DbxClientV2(config, ACCESS_TOKEN)
    }
}