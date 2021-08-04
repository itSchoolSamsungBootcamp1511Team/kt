package com.example.bootcamp.accountfragmentui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.databinding.FragmentAccountBinding
import com.example.bootcamp.databinding.Viewpager2FeedBinding


class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val user = AuthUser.getInstance()!!

        val myPosts = mutableListOf(Post(-1, -1, -1, "", ""))
        user.setMyPosts()
        user.setMyPostsId()
        myPosts.addAll(user.myPosts)

        binding.profile.layoutManager = LinearLayoutManager(this.context)
        val adapter = AccountFeedListAdapter(myPosts, this.requireActivity())
        binding.profile.adapter = adapter

        return binding.root
    }


}