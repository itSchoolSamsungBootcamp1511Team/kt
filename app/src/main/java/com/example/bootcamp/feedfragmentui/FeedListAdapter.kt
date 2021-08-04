package com.example.bootcamp.feedfragmentui

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.example.bootcamp.R
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.databinding.LayoutFeedItemBinding


open class FeedListAdapter(
    val list: MutableList<Post>,
    val activity: Activity
): RecyclerView.Adapter<FeedListAdapter.AbstractItemHolder>() {

    abstract class AbstractItemHolder(view: View): RecyclerView.ViewHolder(view){
        open val nameHolder = "AbstractHolder"
        open fun bind(position: Int){}
    }

    inner class ItemHolder(private val binding: LayoutFeedItemBinding) : AbstractItemHolder(binding.root){
        override val nameHolder = "ItemHolder"
        override fun bind(position: Int){
            val post = list[position]
            val user = AuthUser.getInstance()!!

            binding.text.text = post.data
            binding.userName.text = "Lorem Ipsum ${post.userId}"

            binding.comments.setOnClickListener { Toast.makeText(activity, "Comments in develop", Toast.LENGTH_SHORT).show() }
            binding.moreAction.setOnClickListener { Toast.makeText(activity, "More Action in develop", Toast.LENGTH_SHORT).show() }
            binding.like.setOnClickListener {
                if (post.id in user.likedPostsId) {
                    binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
                    user.likedPostsId.remove(post.id)
                    user.likedPosts.remove(post)
                } else {
                    binding.like.setImageResource(R.drawable.ic_icon_like_liked)
                    user.likedPostsId.add(post.id)
                    user.likedPosts.add(post)
                }
                fragmentList[0]!!.getBinding().feed.adapter?.notifyDataSetChanged()
                if (fragmentList.size == 2) fragmentList[1]!!.getBinding().feed.adapter?.notifyDataSetChanged()
            }

            if(post.id in user.likedPostsId){
                binding.like.setImageResource(R.drawable.ic_icon_like_liked)
            } else {
                binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
            }

            if(post.userId == user.id){
                binding.moreAction.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractItemHolder {
        return ItemHolder(LayoutFeedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AbstractItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size


}
