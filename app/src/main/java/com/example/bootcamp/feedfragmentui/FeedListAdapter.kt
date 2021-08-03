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
import com.example.bootcamp.models.User

class FeedListAdapter(
    val list: MutableList<Post>,
    val activity: Activity,
): RecyclerView.Adapter<FeedListAdapter.ItemHolder>() {


    inner class ItemHolder(private val binding: LayoutFeedItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val post = list[position]

            binding.text.text = post.text
            binding.userName.text = "Lorem Ipsum ${post.user_uid}"

            binding.comments.setOnClickListener { Toast.makeText(activity, "Comments in develop", Toast.LENGTH_SHORT).show() }
            binding.moreAction.setOnClickListener { Toast.makeText(activity, "More Action in develop", Toast.LENGTH_SHORT).show() }
            binding.like.setOnClickListener {
                if (post.postId in currentUser.likedPostsId) {
                    binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
                    currentUser.likedPostsId.remove(post.postId)
                    User.likedPosts.remove(post)
                } else {
                    binding.like.setImageResource(R.drawable.ic_icon_like_liked)
                    currentUser.likedPostsId.add(post.postId)
                    User.likedPosts.add(post)
                }
                fragmentList[0].getBinding().feed.adapter?.notifyDataSetChanged()
                if (fragmentList.size == 2) fragmentList[1].getBinding().feed.adapter?.notifyDataSetChanged()
            }

            if(post.postId in currentUser.likedPostsId){
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