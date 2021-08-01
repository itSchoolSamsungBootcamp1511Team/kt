package com.example.bootcamp.feedfragmentui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FeedAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val TAB_TITLES = arrayOf(
        "All",
        "Liked"
    )

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}