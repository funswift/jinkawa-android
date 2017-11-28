package com.example.taross.view

/**
 * Created by taross on 2017/11/05.
 */
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.support.design.R.id.text
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.NCMBFile
import android.system.Os.mkdir
import android.widget.LinearLayout
import okhttp3.OkHttpClient
import okhttp3.Request
import android.os.AsyncTask.execute
import android.util.Log
import android.os.StrictMode
import android.os.Build.VERSION.SDK_INT
import com.dropbox.core.android.Auth
import java.io.*
import android.content.Intent
import com.example.taross.jinkawa_android.*
import java.net.URI


class CustomBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var saveInDeviceButton:ImageButton
    lateinit var saveInDropboxButton:ImageButton
    lateinit var event:Event
    lateinit var activity:Activity

    var state = 0 //csvの保存先を示す

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.layout_buttom_sheet, null)
        saveInDeviceButton = view!!.findViewById(R.id.button_save_device) as ImageButton
        saveInDropboxButton =view!!.findViewById(R.id.button_save_dropbox) as ImageButton
        dialog.setContentView(view)

        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //your codes here


        saveInDeviceButton.setOnClickListener {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://mb.api.cloud.nifty.com/2013-09-01/applications/zUockxBwPHqxceBH/publicFiles/${event.id}.csv")
                    .build()
            client.newCall(request).execute().use { response ->
                val fileDir:String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path

                val fileName = "$fileDir/${event.title}.csv"
                val writeText =  response.body()?.string().toString()

                val file = File(fileName)
                file.writeText(writeText, Charsets.UTF_16)

                Log.d("result", "OK! CSV was wrote! writeText is $writeText")
                state = 1

                dismiss()
            }
        }

        saveInDropboxButton.setOnClickListener{
            Auth.startOAuth2Authentication(context, getString(R.string.APP_KEY))
        }

    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    fun getAccessToken(){
        val token = Auth.getOAuth2Token()
        if(token != null){
            val prefs = context.getSharedPreferences("com.example.taross.dropboxintegration", Context.MODE_PRIVATE)
            prefs.edit().putString("access-token", token).apply()
        }
        upload()
    }

    override fun onActivityResult(requestCode: Int, resultCode:Int, data:Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        val prefs = context.getSharedPreferences("com.example.taross.dropboxintegration", Context.MODE_PRIVATE)
        val accessToken:String = prefs.getString("access-token", "")

        if (resultCode != RESULT_OK || data == null) return
         // Check which request we're responding to
        if (requestCode == 5237) {
        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
          //Image URI received
            val file= File(data.toUri(Intent.URI_INTENT_SCHEME))
            if (file != null) {
              //Initialize UploadTask
              UploadTask(DropboxClient.getClient(accessToken), file, context).execute();
          }
      }
  }
}


    private fun upload() {
        val prefs = context.getSharedPreferences("com.example.taross.dropboxintegration", Context.MODE_PRIVATE)
        val accessToken:String = prefs.getString("access-token", "")

        if (accessToken.isNotEmpty()) return
        //Select image to upload
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(Intent.createChooser(intent,
                "Upload to Dropbox"),5237)
    }

    override fun onPause() {
        super.onPause()
        when(state){
            1 -> {
                Snackbar
                        .make(getActivity().findViewById(R.id.layout_participants_list) as LinearLayout, "Downloadsへの保存が完了しました", Snackbar.LENGTH_LONG)
                        .show()
            }
        }
    }

    companion object {

        fun newInstance(): CustomBottomSheetDialog {
            return CustomBottomSheetDialog()
        }
    }
}