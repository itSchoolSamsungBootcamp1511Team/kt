package com.example.bootcamp.feedfragmentui

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.example.bootcamp.R
import com.example.bootcamp.Utils
import com.example.bootcamp.dataClasses.*
import com.example.bootcamp.databinding.LayoutFeedItemBinding
import com.google.firebase.database.FirebaseDatabase


open class FeedListAdapter(
    var list: MutableList<Post>,
    val activity: Activity
): RecyclerView.Adapter<FeedListAdapter.AbstractItemHolder>() {

    abstract class AbstractItemHolder(view: View): RecyclerView.ViewHolder(view){
        open fun bind(position: Int){}
    }

    inner class ItemHolder(private val binding: LayoutFeedItemBinding) : AbstractItemHolder(binding.root){
        override fun bind(position: Int){
            val post = list[position]
            val user = AuthUser.getInstance()!!

            binding.text.text = post.text
            binding.userName.text = UserBase.findUserById(post.userId)!!.name

            binding.comments.setOnClickListener { Toast.makeText(activity, "Comments in develop", Toast.LENGTH_SHORT).show() }
            binding.moreAction.setOnClickListener { Toast.makeText(activity, "More Action in develop", Toast.LENGTH_SHORT).show() }
            binding.like.setOnClickListener {
                if (post.id in user.likedPostsId) {
                    binding.like.setImageResource(R.drawable.ic_icon_like_dontliked)
                    user.likedPostsId.remove(post.id)
                    user.setLikedPosts()
                    val korutina = Korutina()
                    korutina.execute()
                } else {
                    binding.like.setImageResource(R.drawable.ic_icon_like_liked)
                    user.likedPostsId.add(post.id)
                    user.setLikedPosts()
                    val korutina = Korutina()
                    korutina.execute()
                }
                (fragmentList[0].getBinding().feed.adapter as FeedListAdapter).list = PostBase.getInstance()!!
                fragmentList[0].getBinding().feed.adapter?.notifyDataSetChanged()

                if (fragmentList.size == 2) {

                    (fragmentList[1].getBinding().feed.adapter as FeedListAdapter).list = user.likedPosts
                    fragmentList[1].getBinding().feed.adapter?.notifyDataSetChanged()
                }
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
