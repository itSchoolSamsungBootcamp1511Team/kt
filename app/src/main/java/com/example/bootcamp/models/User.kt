package com.example.bootcamp.models

class User(
    var uid: String,
    var name: String,
    var surname: String,
) {
    var likedPosts: MutableList<String> = mutableListOf()

}