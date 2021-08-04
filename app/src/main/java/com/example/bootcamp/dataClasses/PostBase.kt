package com.example.bootcamp.dataClasses

class PostBase {
        companion object {
            private var currentPosts: ArrayList<Post>? = null

            fun getInstance(): ArrayList<Post>? {
                return currentPosts
            }

            fun setInstance(users: ArrayList<Post>) {
                currentPosts = users
            }
        }
}