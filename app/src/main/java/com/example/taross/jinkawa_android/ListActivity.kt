package com.example.taross.jinkawa_android

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar

import com.example.taross.jinkawa_android.EventDetailActivity


class ListActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        val mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager.adapter = mSectionsPagerAdapter


        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_storage_black_24dp)

        if (LoginManager.isLogin) {
            val fab = findViewById(R.id.fab) as FloatingActionButton
            fab.show()
            fab.setOnClickListener {
                if(mViewPager.currentItem == 0) {
                    startActivity(Intent(applicationContext, EventCreateActivity::class.java))
                }else if(mViewPager.currentItem == 1){
                    startActivity(Intent(applicationContext, NoticeCreateActivity::class.java))
                }
            }
        }
        else{
            val fab = findViewById(R.id.fab) as FloatingActionButton
            fab.hide()
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            val fragment = ListFragment.newInstance(position + 1)

            return fragment
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return ""
                1 -> return "SECTION 2"
                2 -> return "SECTION 3"
            }
            return null
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class ListFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_list, container, false)
            val page = arguments.getInt(ARG_SECTION_NUMBER)
            val listView = rootView.findViewById(R.id.listView) as ListView

            val listAdapter = when (page){
                1 -> EventListAdapter(context)
                2 -> NoticeListAdapter(context)
                else -> EventListAdapter(context)
            }

            listView.adapter = listAdapter
            listView.setOnItemClickListener { parent, view, position, id ->
                when (page) {
                    1 -> EventDetailActivity.intent(context, EventListAdapter(context).items[position]).let {
                        startActivity(it)
                    }
                }
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
            fun newInstance(sectionNumber: Int): ListFragment {
                val fragment = ListFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

}
