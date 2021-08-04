package com.example.bootcamp.dataClasses

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class PostBase {
        companion object {
            private var currentPosts: ArrayList<Post>? = null

            fun getInstance(): ArrayList<Post>? {
                return currentPosts
            }

            fun setInstance(users: ArrayList<Post>) {
                currentPosts = users
                currentPosts!!.sortBy { it.time }
                Collections.reverse(currentPosts)
                for (i in currentPosts!!) {
                    Log.d("Posts", i.toString())
                }
            }
            fun findPostById(idChel: Int): Post? {
                for (i in currentPosts!!)
                    if (i.id == idChel)
                        return i
                return null
            }
        }
}