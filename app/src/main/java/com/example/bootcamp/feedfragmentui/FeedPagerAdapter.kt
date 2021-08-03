package com.example.bootcamp.feedfragmentui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

val TAB_TITLES = arrayOf(
    "All",
    "Liked"
)
val fragmentList: MutableList<FeedListFragment> = mutableListOf()

class FeedPagerAdapter(private val fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedListFragment()
        fragment.setBinding(fa)
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT, TAB_TITLES[position])
        }
        fragment.isCreated = true

        when(fragmentList.size){
            0, 1 -> { fragmentList.add(fragment) }
            else -> { fragmentList[position] = fragment }
        }

        return fragmentList[position]
    }

}