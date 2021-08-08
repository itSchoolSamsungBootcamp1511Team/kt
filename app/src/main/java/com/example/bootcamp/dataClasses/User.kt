package com.example.bootcamp.dataClasses

data class User(
        var id: String,
        var name: String,
        var avatar: String,
        var status: String,
        var likedPostsId: ArrayList<String>) {

        var likedPosts: MutableList<Post> = mutableListOf()
        var myPosts: MutableList<Post> = mutableListOf()
        var myPostsId: MutableList<String> = mutableListOf()

        fun setLikedPosts(){
                val ans = ArrayList<Post>()
                for (i in likedPostsId)
                        if (PostBase.findPostById(i) != null)
                                ans.add(PostBase.findPostById(i)!!)
                likedPosts = ans
        }

        fun setMyPosts() {
                val ans = ArrayList<Post>()
                for (i in PostBase.getInstance()!!)
                        if (i.userId == id)
                                ans.add(i)
                myPosts = ans
        }

        fun setMyPostsId() {
                val ans = ArrayList<String>()
                for (i in PostBase.getInstance()!!)
                        if (i.userId == id)
                                ans.add(i.id)
                myPostsId = ans
        }

}


