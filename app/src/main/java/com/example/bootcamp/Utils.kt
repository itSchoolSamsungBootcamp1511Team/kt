package com.example.bootcamp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.bootcamp.dataClasses.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Utils {
    companion object{

        private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun checkEmail(email: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                            return true
                        }
                    }
                }
            }
            return false
        }

        fun fillUsers() {
            val users = ArrayList<User>()
            val myBase = FirebaseDatabase.getInstance().reference
            myBase.child("users").addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        val liked = ArrayList<String>()
                        for (j in i.child("likedPostsId").children)
                            liked.add(j.value.toString())

                        users.add(
                            User(
                            i.key.toString(),
                            i.child("name").value.toString(),
                            i.child("avatar").value.toString(),
                            i.child("status").value.toString(),
                            liked)
                        )
                    }
                    UserBase.setInstance(users)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        fun fillPosts() {
            val posts = ArrayList<Post>()
            val myBase = FirebaseDatabase.getInstance().reference
            myBase.child("posts").addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (i in snapshot.children) {
                        posts.add(
                            Post(
                                i.key.toString(),
                                i.child("userUID").value.toString(),
                                i.child("time").value.toString().toLong(),
                                i.child("text").value.toString())
                        )
                    }
                    PostBase.setInstance(posts)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        fun fillUser() {
            val myId = Firebase.auth.currentUser?.uid
            val now = AuthUser.getInstance()
            if (now == null) {
                val myBase = FirebaseDatabase.getInstance().reference;
                myBase.child("users").child(myId.toString()).addValueEventListener(object:
                    ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var liked = ArrayList<String>()
                        for (i in dataSnapshot.child("likedPostsId").children)
                            liked.add(i.value.toString())

                        if (liked.size == 1 && liked[0] == "") liked = ArrayList()

                        val curUser = myId?.let {
                            User(
                                it,
                                dataSnapshot.child("name").value.toString(),
                                dataSnapshot.child("avatar").value.toString(),
                                dataSnapshot.child("status").value.toString(),
                                liked)
                        }
                        if (curUser != null) {
                            AuthUser.setInstance(curUser)
                        }

                    }
                })
            }
        }
        fun fillInfo() {
            fillPosts()
            fillUsers()
            fillUser()
        }
        fun sosan() {
            FirebaseDatabase.getInstance().reference.child("users")
                .child(AuthUser.getInstance()!!.id)
                .child("likedPostsId").setValue(AuthUser.getInstance()!!.likedPostsId)
        }
    }

}