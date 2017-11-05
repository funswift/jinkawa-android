package com.example.taross.view

/**
 * Created by taross on 2017/11/05.
 */
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Environment
import android.support.design.R.id.text
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.taross.jinkawa_android.R
import com.example.taross.model.Event
import com.nifty.cloud.mb.core.NCMBFile
import android.system.Os.mkdir
import android.widget.LinearLayout
import com.example.taross.jinkawa_android.LoginManager
import okhttp3.OkHttpClient
import okhttp3.Request
import android.os.AsyncTask.execute
import android.util.Log
import android.os.StrictMode
import android.os.Build.VERSION.SDK_INT
import java.io.*


class CustomBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var saveInDeviceButton:ImageButton
    lateinit var event:Event
    lateinit var activity:Activity

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.layout_buttom_sheet, null)
        saveInDeviceButton = view!!.findViewById(R.id.button_save_device) as ImageButton
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
                file.writeText(writeText)

                Log.d("result", "OK! CSV was wrote! writeText is $writeText")

                dismiss()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Snackbar
                .make(getActivity().findViewById(R.id.layout_participants_list) as LinearLayout, "Downloadsへの保存が完了しました", Snackbar.LENGTH_LONG)
                .show()
    }

    companion object {

        fun newInstance(): CustomBottomSheetDialog {
            return CustomBottomSheetDialog()
        }
    }
}