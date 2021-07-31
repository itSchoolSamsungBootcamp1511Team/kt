package com.example.bootcamp.dataClasses

data class User(
        var name: String,
        var surname: String,
        var avatar: String,
        var status: String,
        var id: Int,
        var myPosts: ArrayList<Int>,
        var likedPosts: ArrayList<Int>,
        var likedComments: ArrayList<Int>) {

}


