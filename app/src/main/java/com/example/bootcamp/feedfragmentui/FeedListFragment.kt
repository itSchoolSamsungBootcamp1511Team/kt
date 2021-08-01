package com.example.bootcamp.feedfragmentui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcamp.R
import com.example.bootcamp.currentUser
import com.example.bootcamp.databinding.Viewpager2FeedBinding
import com.example.bootcamp.models.Post

const val ARG_OBJECT = "FeedList"

tailrec fun Context.getActivity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.getActivity()

class FeedListFragment: Fragment() {
    private lateinit var binding: Viewpager2FeedBinding

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
            val listPosts: MutableList<Post> = Post.createTestList()
            var feedListAdapter: FeedListAdapter? = null
            val activity = view.context.getActivity()
            if (getString(ARG_OBJECT) == TAB_TITLES[0]){
                feedListAdapter = activity?.let { FeedListAdapter(listPosts, it) }!!
            } else if(getString(ARG_OBJECT) == TAB_TITLES[1]){
                val likedPosts = mutableListOf<Post>()
                for(post in listPosts){
                    if(post.postId in currentUser.likedPosts){
                        likedPosts.add(post)
                    }
                }
                feedListAdapter = activity?.let { FeedListAdapter(likedPosts, it) }!!
            }
            view.findViewById<RecyclerView>(R.id.feed).layoutManager = activity?.let { LinearLayoutManager(it) }
            view.findViewById<RecyclerView>(R.id.feed).adapter = feedListAdapter
            Log.e("ViewLog", "View Created")
            if (feedListAdapter != null) {
                Log.e("PostList", feedListAdapter.list.toString())
            }
        }
    }
}