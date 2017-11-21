package com.example.taross.jinkawa_android

import android.content.Context
import android.widget.Toast
import com.dropbox.core.DbxException
import com.dropbox.core.v2.files.WriteMode
import com.dropbox.core.v2.DbxClientV2
import android.os.AsyncTask
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.IOException


/**
 * Created by taross on 2017/11/22.
 */
class UploadTask internal constructor(private val dbxClient: DbxClientV2, private val file: File, private val context: Context) : AsyncTask<Any, Any, Any>() {

    protected override fun doInBackground(params: Array<Any>): Any? {
        val value: Any = try {
            // Upload to Dropbox
            val inputStream = FileInputStream(file)
            dbxClient.files().uploadBuilder("/" + file.getName()) //Path in the user's Dropbox to save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream)
            Log.d("Upload Status", "Success")
        } catch (e: DbxException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    protected override fun onPostExecute(o: Any) {
        super.onPostExecute(o)
        Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
    }
}