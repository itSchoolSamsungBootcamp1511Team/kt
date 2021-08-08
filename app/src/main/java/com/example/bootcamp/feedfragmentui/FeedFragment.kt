package com.example.bootcamp.feedfragmentui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bootcamp.AddPostActivity
import com.example.bootcamp.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        binding.viewPager2.adapter = FeedPagerAdapter(this.requireActivity())

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        binding.addPostButton.setOnClickListener {
            val intent = Intent(this.context, AddPostActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}