
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

class NoticeListAdapter(private val context: Context): LoadableListAdapter<Notice>(){
    override var items: MutableList<Notice> = loadData()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = ((convertView as? NoticeItemView) ?: NoticeItemView(context)).apply {
        setItem(items[position])
    }

    override fun loadData():MutableList<Notice>{
        val noticeList:MutableList<Notice> = mutableListOf<Notice>()

        val query: NCMBQuery<NCMBObject> = NCMBQuery("Information")
        query.addOrderByDescending("updateDate")
        val results: List<NCMBObject> = try {
            query.find()
        } catch (e : Exception) { emptyList<NCMBObject>() }
        if (results.isNotEmpty()) {
            for(result in results){
                val rs_updateDate = result.getString("updateDate")
                val mrs_updateDate = rs_updateDate.substring(0, 19).replace("T", " ")
                val notice: Notice = Notice(
                        result.getString("title"),
                        result.getString("objectId"),
                        result.getString("department_name"),
                        result.getString("date"),
                        result.getString("info"),
                        mrs_updateDate,
                        result.getString("type"),
                        result.getBoolean("officer_only")
                )
                noticeList.add(notice)
            }
        }
        return noticeList
    }

    override fun reflesh() {
        this.items = loadData()
        this.filter()
    }

    override fun filter(){
        if(!LoginManager.isLogin)
            this.items = items.filter { it.officer_only == false }.toMutableList()
    }
}
