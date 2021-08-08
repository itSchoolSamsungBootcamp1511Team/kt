package com.example.bootcamp.accountfragmentui

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bootcamp.R
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.dataClasses.PostBase
import com.example.bootcamp.dataClasses.UserBase
import com.example.bootcamp.databinding.LayoutAccountItemBinding
import com.example.bootcamp.databinding.LayoutFeedItemBinding
import com.example.bootcamp.feedfragmentui.FeedListAdapter
import com.example.bootcamp.feedfragmentui.fragmentList
import com.google.firebase.database.FirebaseDatabase

class AccountFeedListAdapter(
    list: MutableList<Post>,
    activity: Activity) :
    FeedListAdapter(list, activity) {

    inner class ProfileItemHolder(private val binding: LayoutAccountItemBinding): AbstractItemHolder(binding.root){
        override fun bind(position: Int) {
            val user = AuthUser.getInstance()!!

            binding.name.text = user.name
            binding.countPosts.text = "${user.myPosts.size}"
            binding.countLikes.text = "${user.likedPosts.size}"
            binding.status.text = user.status

            binding.editButton.setOnClickListener {
                Toast.makeText(activity, "In develop", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class ItemHolder2(private val binding: LayoutFeedItemBinding, private val adapter: AccountFeedListAdapter) : AbstractItemHolder(binding.root){
        override fun bind(position: Int) {
            val post = list[position]
            val user = AuthUser.getInstance()!!

            binding.text.text = post.text
            binding.userName.text = UserBase.findUserById(post.userId)!!.name

            binding.comments.setOnClickListener {
                Toast.makeText(
                    activity,
                    "Comments in develop",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.moreAction.setOnClickListener {
                Toast.makeText(
                    activity,
                    "More Action in develop",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.like.setOnClickListener {
                if (post.id in user.likedPostsId) {
                    binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
                    user.likedPostsId.remove(post.id)
                    FirebaseDatabase.getInstance().reference.child("users")
                        .child(AuthUser.getInstance()!!.id)
                        .child("likedPostsId").setValue(user.likedPostsId)
                } else {
                    binding.like.setImageResource(R.drawable.ic_icon_like_liked)
                    user.likedPostsId.add(post.id)
                    FirebaseDatabase.getInstance().reference.child("users")
                        .child(AuthUser.getInstance()!!.id)
                        .child("likedPostsId").setValue(user.likedPostsId)
                }
                adapter.notifyDataSetChanged()
            }

            if (post.id in user.likedPostsId) {
                binding.like.setImageResource(R.drawable.ic_icon_like_liked)
            } else {
                binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
            }

            if(post.userId == user.id){
                binding.moreAction.visibility = View.VISIBLE
            }

        }
    }

    override fun getItemViewType(position: Int): Int =
        when(list[position].id){
            "-1" -> {1}
            else -> {0}
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractItemHolder {
        if(viewType == 1){
            return ProfileItemHolder(
                LayoutAccountItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        return ItemHolder2(
            LayoutFeedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false), this)
    }
}