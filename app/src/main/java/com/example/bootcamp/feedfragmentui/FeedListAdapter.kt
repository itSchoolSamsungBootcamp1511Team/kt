package com.example.bootcamp.feedfragmentui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcamp.R
import com.example.bootcamp.currentUser
import com.example.bootcamp.databinding.LayoutFeedItemBinding
import com.example.bootcamp.models.Post

class FeedListAdapter(
    val list: MutableList<Post>,
    val activity: Activity
): RecyclerView.Adapter<FeedListAdapter.ItemHolder>() {


    inner class ItemHolder(private val binding: LayoutFeedItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val post = list[position]

            binding.text.text = post.text
            binding.userName.text = "Lorem Ipsum ${post.user_uid}"

            binding.comments.setOnClickListener { Toast.makeText(activity, "Comments in develop", Toast.LENGTH_SHORT).show() }
            binding.moreAction.setOnClickListener { Toast.makeText(activity, "More Action in develop", Toast.LENGTH_SHORT).show() }
            binding.like.setOnClickListener {
                if(post.postId in currentUser.likedPosts){
                    binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
                    currentUser.likedPosts.remove(post.postId)
                } else {
                    binding.like.setImageResource(R.drawable.ic_icon_like_liked)
                    currentUser.likedPosts.add(post.postId)
                }
            }

            if(post.postId in currentUser.likedPosts){
                binding.like.setImageResource(R.drawable.ic_icon_like_liked)
            } else {
                binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
            }

            if(post.user_uid != currentUser.uid){
                binding.moreAction.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutFeedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size
}