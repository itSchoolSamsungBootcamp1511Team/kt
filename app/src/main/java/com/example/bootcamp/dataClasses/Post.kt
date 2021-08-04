package com.example.bootcamp.dataClasses

import java.lang.System.currentTimeMillis
import kotlin.random.Random

data class Post(
        var id: Int,
        var userId: Int,
        var time: Long,
        var photos: String,
        var data: String,
        var likes: Int,
        var comments: ArrayList<Comment?>) {

        /* just fo test */
        companion object{
                private var count = 0

                fun createTestList(): MutableList<Post>{
                        val postList = mutableListOf<Post>()
                        val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Hac ac ut viverra sagittis, turpis lobortis nisl. Faucibus est tristique in egestas rhoncus ac metus. Senectus ultrices in at ac lectus. Nullam sed nunc a, ultricies."
                        for(i in 1..20) {
                                val post = Post(
                                        count++,
                                        Random.nextInt(1, 1000) % 3,
                                        currentTimeMillis(),
                                        "photos",
                                        text,
                                        0,
                                        mutableListOf<Comment?>(null) as ArrayList<Comment?>
                                )

                                postList.add(post)
                        }
                        return postList
                }
        }

}
