package com.example.bootcamp.models

class User(
    var uid: String,
    var name: String,
    var surname: String,
) {
    var likedPostsId: MutableList<String> = mutableListOf()

    companion object{
        var likedPosts: MutableList<Post> = mutableListOf()
    }

}