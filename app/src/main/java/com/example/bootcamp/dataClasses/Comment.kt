package com.example.bootcamp.dataClasses

data class Comment(
        var id: Int,
        var userId: Int,
        var postId: Int,
        var time: Long,
        var data: String,
        var photos: String,
        var likes: Int) {

}