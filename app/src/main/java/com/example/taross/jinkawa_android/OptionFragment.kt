package com.example.taross.jinkawa_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch

/**
 * Created by y_snkw on 2017/11/07.
 */
class OptionFragment: Fragment() {
    var pushButton: Switch? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater!!.inflate(R.layout.option_fragment, container, false)
        pushButton = rootView.findViewById(R.id.option_setting_notification_switch) as? Switch

        pushButton?.setOnCheckedChangeListener { buttonView, isChecked ->
            UserConfig.is_pushed = isChecked
            UserConfig.sendPushConfigChange()
            Log.d("プッシュ通知チャンネル変更", "${isChecked}に変更されました")
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