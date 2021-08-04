package com.example.bootcamp.accountfragmentui

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.databinding.LayoutAccountItemBinding
import com.example.bootcamp.databinding.LayoutFeedItemBinding
import com.example.bootcamp.feedfragmentui.FeedListAdapter

class AccountFeedListAdapter(
    list: MutableList<Post>,
    activity: Activity) :
    FeedListAdapter(list, activity) {

    inner class ProfileItemHolder(private val binding: LayoutAccountItemBinding): AbstractItemHolder(binding.root){
        override val nameHolder = "ProfileItemHolder"
        override fun bind(position: Int) {
            val user = AuthUser.getInstance()!!

            binding.name.text = "${user.name}  ${user.surname}"
            binding.countPosts.text = "49"
            binding.countLikes.text = "49"
            binding.status.text = user.status

        }
    }

    override fun getItemViewType(position: Int): Int =
        when(list[position].id){
            -1 -> {1}
            else -> {0}
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractItemHolder {
        if(viewType == 1){
            return ProfileItemHolder(
                LayoutAccountItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        return ItemHolder(
            LayoutFeedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }
}