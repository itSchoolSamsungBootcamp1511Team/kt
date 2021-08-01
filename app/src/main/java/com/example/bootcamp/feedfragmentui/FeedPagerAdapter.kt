package com.example.bootcamp.feedfragmentui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

val TAB_TITLES = arrayOf(
    "All",
    "Liked"
)

class FeedPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedListFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT, TAB_TITLES[position])
        }
        return fragment
    }
}