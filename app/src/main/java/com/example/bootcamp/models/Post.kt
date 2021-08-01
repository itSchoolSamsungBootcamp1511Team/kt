package com.example.bootcamp.models

import kotlin.random.Random

class Post(
    var postId: String,
    var text: String,
    var user_uid: String){

    companion object{
        fun createTestList(): MutableList<Post>{
            val testList = mutableListOf<Post>()
            for (i in 1..100){
                testList.add(
                    Post(i.toString(),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Hac ac ut viverra sagittis, turpis lobortis nisl. Faucibus est tristique in egestas rhoncus ac metus. Senectus ultrices in at ac lectus. Nullam sed nunc a, ultricies.",
                        (Random.nextInt()%4).toString()))
            }
            return testList
        }
    }
}