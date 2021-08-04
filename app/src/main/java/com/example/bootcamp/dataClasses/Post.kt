package com.example.bootcamp.dataClasses

data class Post(
        var id: Int,
        var userId: Int,
        var time: Long,
        var photos: String,
        var data: String) {
}
