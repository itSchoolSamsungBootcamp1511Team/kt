package com.example.bootcamp.feedfragmentui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bootcamp.dataClasses.AuthUser
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

        val user = AuthUser.getInstance()
        Log.e("UserTag", user.toString())

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        binding.addPostButton.setOnClickListener {
            Toast.makeText(this.context, "Add Post in develop", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

}