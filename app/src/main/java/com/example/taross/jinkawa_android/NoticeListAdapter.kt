
/**
 * Created by taross on 2017/08/14.
 */
package com.example.taross.jinkawa_android

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.example.taross.model.Notice
import com.example.taross.view.NoticeItemView
import com.nifty.cloud.mb.core.NCMB
import com.nifty.cloud.mb.core.NCMBException
import com.nifty.cloud.mb.core.NCMBObject
import com.nifty.cloud.mb.core.FetchCallback
import com.nifty.cloud.mb.core.NCMBQuery


/**
 * Created by taross on 2017/08/12.
 */

class NoticeListAdapter(private val context: Context): BaseAdapter(){
    var items: MutableList<Notice> = noticeLoad()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = ((convertView as? NoticeItemView) ?: NoticeItemView(context)).apply {
        setItem(items[position])
    }

    fun noticeLoad():MutableList<Notice>{
        val noticeList:MutableList<Notice> = mutableListOf<Notice>()

        val query: NCMBQuery<NCMBObject> = NCMBQuery("Information")
        val results: List<NCMBObject> = try {
            query.find()
        } catch (e : Exception) { emptyList<NCMBObject>() }
        if (results.isNotEmpty()) {
            for(result in results){
                val notice: Notice = Notice(result.getString("title"))
                noticeList.add(notice)
            }
        }
        return noticeList
    }
}