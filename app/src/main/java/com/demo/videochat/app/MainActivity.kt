package com.demo.videochat.app

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Videos"))
        tabLayout.addTab(tabLayout.newTab().setText("Feeds"))
        tabLayout.tabGravity = TabLayout.GRAVITY_START
        val adapter = MyAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        setSelectTab(0)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                setSelectTab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                setDeselectTab(tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setSelectTab(position: Int) {
        val tabStrip = tabLayout.getChildAt(0) as ViewGroup
        val tabView: View = tabStrip.getChildAt(position)

        val paddingStart: Int = tabView.paddingStart
        val paddingTop: Int = tabView.paddingTop
        val paddingEnd: Int = tabView.paddingEnd
        val paddingBottom: Int = tabView.paddingBottom
        ViewCompat.setBackground(
            tabView,
            AppCompatResources.getDrawable(tabView.context, R.drawable.tab_select)
        )
        ViewCompat.setPaddingRelative(
            tabView,
            paddingStart,
            paddingTop,
            paddingEnd,
            paddingBottom
        )
    }

    private fun setDeselectTab(position: Int) {
        val tabStrip = tabLayout.getChildAt(0) as ViewGroup
        val tabView: View = tabStrip.getChildAt(position)

        val paddingStart: Int = tabView.paddingStart
        val paddingTop: Int = tabView.paddingTop
        val paddingEnd: Int = tabView.paddingEnd
        val paddingBottom: Int = tabView.paddingBottom
        ViewCompat.setBackground(
            tabView,
            AppCompatResources.getDrawable(tabView.context, R.drawable.tab_unselect)
        )
        ViewCompat.setPaddingRelative(
            tabView,
            paddingStart,
            paddingTop,
            paddingEnd,
            paddingBottom
        )
    }
}