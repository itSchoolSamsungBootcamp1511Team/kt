package com.example.bootcamp.dataClasses

data class User(
        var id: Int,
        var name: String,
        var surname: String,
        var avatar: String,
        var status: String,
        var liked: ArrayList<Int>) {
        //val likedPosts: MutableList<Post> = mutableListOf()
        //val myPosts: MutableList<Post> = mutableListOf()
        fun getLikedPosts(): ArrayList<Post> {
                var ans = ArrayList<Post>()
                for (i in liked)
                        if (PostBase.findPostById(i) != null)
                                ans.add(PostBase.findPostById(i)!!)
                return ans
        }

        fun getLikedPostsId(): ArrayList<Int> {
                var ans = ArrayList<Int>()
                for (i in liked)
                        if (PostBase.findPostById(i) != null)
                                ans.add(PostBase.findPostById(i)!!.id)
                return ans
        }

        fun getMyPosts(): ArrayList<Post> {
                var ans = ArrayList<Post>()
                for (i in PostBase.getInstance()!!)
                        if (i.userId == id)
                                ans.add(i)
                return ans
        }

        fun getMyPostsId(): ArrayList<Int> {
                var ans = ArrayList<Int>()
                for (i in PostBase.getInstance()!!)
                        if (i.userId == id)
                                ans.add(i.id)
                return ans
        }

}


