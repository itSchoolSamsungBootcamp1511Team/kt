package com.example.bootcamp.feedfragmentui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcamp.R
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.PostBase
import com.example.bootcamp.databinding.Viewpager2FeedBinding


const val ARG_OBJECT = "FeedList"

class FeedListFragment: Fragment() {
    private lateinit var binding: Viewpager2FeedBinding
    var isCreated: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Viewpager2FeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val feed = view.findViewById<RecyclerView>(R.id.feed)
            val tabTitle = getString(ARG_OBJECT)
            var adapter = FeedListAdapter(PostBase.getInstance()!!, view.context as Activity)
            if (tabTitle == TAB_TITLES[1]){
                AuthUser.getInstance()!!.setLikedPosts()
                adapter = FeedListAdapter(AuthUser.getInstance()!!.likedPosts, view.context as Activity)
            }
            feed.layoutManager = LinearLayoutManager(view.context)
            feed.adapter = adapter
        }
    }

    fun setBinding(activity: Activity){
        binding = Viewpager2FeedBinding.inflate(activity.layoutInflater)
    }
    fun getBinding() = binding
}