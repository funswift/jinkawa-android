package com.example.taross.jinkawa_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.nifty.cloud.mb.core.NCMBInstallation
import org.json.JSONArray


/**
 * Created by y_snkw on 2017/11/07.
 */
class OptionFragment: Fragment() {
    var pushButton: Switch? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.option_fragment, container, false)
        pushButton = rootView.findViewById(R.id.option_setting_notification_switch) as? Switch

        val pushPref = context.getSharedPreferences(getString(R.string.user_config), Context.MODE_PRIVATE)
        val pushConfig = pushPref.getBoolean(getString(R.string.push), true)

        pushButton?.isChecked = pushConfig

        val editor:SharedPreferences.Editor = pushPref.edit()

        pushButton?.setOnCheckedChangeListener { buttonView, isChecked ->
            val editor:SharedPreferences.Editor = pushPref.edit()
            editor.putBoolean(getString(R.string.push), isChecked)
            editor.commit()

            val installation = NCMBInstallation.getCurrentInstallation()
            installation.channels = if(isChecked){
                JSONArray("[on]")
            } else{
                JSONArray("[off]")
            }
            installation.saveInBackground()
            Log.d("プッシュ通知チャンネル変更", "${isChecked}に変更されました")
        }

        val accountTextView = rootView.findViewById(R.id.option_account) as TextView
        val accountList = rootView.findViewById(R.id.option_account_list) as LinearLayout
        val accountPassLayout = rootView.findViewById(R.id.option_account_pass) as RelativeLayout

        if(LoginManager.isLogin){
            accountTextView.setVisibility(View.VISIBLE)
            accountList.setVisibility(View.VISIBLE)
        }else{
            accountTextView.setVisibility(View.GONE)
            accountList.setVisibility(View.GONE)
        }

        accountPassLayout.setOnClickListener{
            startActivity(Intent(activity, PasswordChangeActivity::class.java))
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): OptionFragment {
            val fragment = OptionFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}