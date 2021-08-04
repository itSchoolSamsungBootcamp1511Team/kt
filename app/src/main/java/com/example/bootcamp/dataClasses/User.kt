package com.example.bootcamp.dataClasses

data class User(
        var id: Int,
        var name: String,
        var surname: String,
        var avatar: String,
        var status: String,
        var otherMeLikes: Int,
        var myPostsId: ArrayList<Int>,
        var likedPostsId: ArrayList<Int>,
        var likedCommentsId: ArrayList<Int>) {

        val likedPosts: MutableList<Post> = mutableListOf()

}


