package com.example.bootcamp.dataClasses

data class User(
        var id: Int,
        var name: String,
        var surname: String,
        var avatar: String,
        var status: String,
        var likedPostsId: ArrayList<Int>) {

        var likedPosts: MutableList<Post> = mutableListOf()
        var myPosts: MutableList<Post> = mutableListOf()
        var myPostsId: MutableList<Int> = mutableListOf()

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
                val ans = ArrayList<Int>()
                for (i in PostBase.getInstance()!!)
                        if (i.userId == id)
                                ans.add(i.id)
                myPostsId = ans
        }

}


