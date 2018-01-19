package com.example.taross.jinkawa_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.util.Linkify.PHONE_NUMBERS
import android.text.util.Linkify.WEB_URLS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.nifty.cloud.mb.core.NCMBInstallation
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.alert
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

        pushButton?.isChecked = UserConfig.getIsPushed(context)

        pushButton?.setOnCheckedChangeListener { buttonView, isChecked ->

            UserConfig.sendPushConfigChange(context, isChecked)

            Log.d("プッシュ通知チャンネル変更", "${isChecked}に変更されました")
        }

        val settingFormLayout = rootView.findViewById(R.id.option_setting_from) as RelativeLayout
        settingFormLayout.setOnClickListener {
            when (UserConfig.getParticipantFlag(context)) {
                true -> alert {
                            title = getString(R.string.option_setting_form_text)
                            customView {
                                val entryInformation = UserConfig.getParticipantData(context)
                                linearLayout {
                                    topPadding = dip(16)
                                    verticalLayout {
                                        padding = dip(16)
                                        textView(getString(R.string.entry_item_name)) { textSize = 18f }
                                        textView(getString(R.string.entry_item_address)) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(getString(R.string.entry_item_tell)) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(getString(R.string.entry_item_gender)) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(getString(R.string.entry_item_age)) { textSize = 18f }.lparams { topMargin = dip(16) }
                                    }
                                    verticalLayout {
                                        padding = dip(16)
                                        textView(entryInformation.name) { textSize = 18f }
                                        textView(entryInformation.address) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(entryInformation.tell) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(entryInformation.gender) { textSize = 18f }.lparams { topMargin = dip(16) }
                                        textView(entryInformation.age) { textSize = 18f }.lparams { topMargin = dip(16) }
                                    }
                                }
                            }
                            positiveButton(getString(R.string._delete)) {
                                alert(getString(R.string.delete_message)) {
                                    title = getString(R.string.confirm)
                                    positiveButton(getString(R.string.yes)) {
                                        UserConfig.resetParticipantData(context)
                                        snackbar(rootView, getString(R.string.delete_entry_log))
                                    }
                                    negativeButton(getString(R.string.no)) {}
                                }.show()
                            }
                            negativeButton(getString(R.string._return)) {}
                        }.show()
                false -> alert {
                    title = getString(R.string.option_setting_form_text)
                    customView {
                        linearLayout {
                            padding = dip(32)
                            textView(getString(R.string.nothing_entry_log)) { textSize = 18f }
                        }
                    }
                    negativeButton(getString(R.string._return)){}
                }.show()
            }
        }

        val accountTextView = rootView.findViewById(R.id.option_account) as TextView
        val accountList = rootView.findViewById(R.id.option_account_list) as LinearLayout
        val accountPassLayout = rootView.findViewById(R.id.option_account_pass) as RelativeLayout
        val accountLogoutLayout = rootView.findViewById(R.id.option_account_logout) as RelativeLayout
        val contactJinkawaLayout = rootView.findViewById(R.id.option_contact_jinkawa) as RelativeLayout

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

        accountLogoutLayout.setOnClickListener {
            LoginManager.logout()
            startActivity(Intent(activity, MainActivity::class.java))
        }

        contactJinkawaLayout.setOnClickListener {
            alert {
                title = getString(R.string.option_setting_contact_jinkawa)
                customView {
                    verticalLayout {
                        padding = dip(16)
                        textView(getString(R.string.contact_address)) { textSize = 18f }
                        val tell = textView(getString(R.string.contact_tell)) {
                            textSize = 18f
                            autoLinkMask = PHONE_NUMBERS
                        }.lparams { topMargin = dip(16) }
                        textView(getString(R.string.contact_facebook)) { textSize = 18f }.lparams { topMargin = dip(16) }
                        textView(getString(R.string.contact_facebook_url)) {
                            textSize = 15f
                            autoLinkMask = WEB_URLS
                        }.lparams { leftMargin = dip(8) }
                    }
                }
                positiveButton(getString(R.string._return)){}
            }.show()
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