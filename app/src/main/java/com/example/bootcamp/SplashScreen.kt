package com.example.bootcamp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.Utils.Companion.isOnline
import com.example.bootcamp.dataClasses.*
import com.example.bootcamp.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        auth = Firebase.auth

        when(intent.getIntExtra("statusLogin", 0)){
            0 -> {
                login()
            }
            1 -> {
                signIn()
            }
        }

        setContentView(binding.root)
    }

    private fun login(){
        if(isOnline(this)) {
            Handler().postDelayed({
                val intent = Intent(this, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }, 2000)
        } else {
            Toast.makeText(this, "You don't have the Internet connection. Try again later", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(){
        val login = intent.getStringExtra("loginUser")!!
        val pass = intent.getStringExtra("passwordUser")!!

        auth.signInWithEmailAndPassword(login, pass).addOnCompleteListener(this){
            if(!it.isSuccessful){
                Toast.makeText(this, "User don't exist", Toast.LENGTH_SHORT).show()
                login()
            } else {
                fillInfo()
                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 7000)
            }
        }
    }

    private fun fillInfo() {
        fillUser();
        fillUsers();
        fillPosts();
    }

    private fun fillUsers() {
        val users = ArrayList<User>()
        val myBase = FirebaseDatabase.getInstance().reference
        myBase.child("users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val liked = ArrayList<String>()
                    for (j in i.child("likes").children)
                        liked.add(j.value.toString())

                    users.add(User(
                        i.key.toString(),
                        i.child("name").value.toString(),
                        i.child("avatar").value.toString(),
                        i.child("status").value.toString(),
                        liked))
                }
                UserBase.setInstance(users)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fillPosts() {
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

    private fun fillUser() {
        val myId = auth.currentUser?.uid
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
                    for (i in dataSnapshot.child("likes").children)
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
}