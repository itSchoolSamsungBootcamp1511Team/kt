package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.*
import com.example.bootcamp.databinding.ActivitySplashScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)

        val statusLogin = intent.getIntExtra("228", 0)
        fillInfo()
        when(statusLogin){
            0 -> {
                Handler().postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                }, 10000)
            }
            1 -> {
                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                }, 10000)
            }
        }

        setContentView(binding.root)
    }

    private fun fillInfo() {
        fillUser();
        fillUsers();
        fillPosts();
    }

    private fun fillUsers() {
        var users = ArrayList<User>()
        val myBase = FirebaseDatabase.getInstance().reference
        myBase.child("users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    var liked = ArrayList<Int>()
                    for (j in i.child("likes").children)
                        liked.add(j.value.toString().toInt())
                    users.add(User(
                        i.key.toString().toInt(),
                        i.child("name").value.toString(),
                        i.child("surname").value.toString(),
                        i.child("avatar").value.toString(),
                        i.child("status").value.toString(),
                        liked))
                }
                UserBase.setInstance(users)
                Log.d("SETUPINFO", users.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fillPosts() {
        var posts = ArrayList<Post>()
        val myBase = FirebaseDatabase.getInstance().reference
        myBase.child("posts").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    posts.add(
                        Post(
                        i.key.toString().toInt(),
                        i.child("userId").value.toString().toInt(),
                        i.child("time").value.toString().toLong(),
                        i.child("photos").value.toString(),
                        i.child("data").value.toString()
                    )
                    )
                }
                PostBase.setInstance(posts)
                Log.d("SETUPINFO", posts.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fillUser() {
        val myId = intent.getIntExtra("228", 1);
        val now = AuthUser.getInstance()
        if (myId == -1 && now == null) {
            Toast.makeText(applicationContext, "Что-то пошло не так!", Toast.LENGTH_LONG).show();
            this.finish();
        }
        if (now == null) {
            val myBase = FirebaseDatabase.getInstance().reference;
            myBase.child("users").child(myId.toString()).addValueEventListener(object:
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var liked = ArrayList<Int>()
                    for (i in dataSnapshot.child("likes").children)
                        liked.add(i.value.toString().toInt())
                    val curUser = User(myId,
                        dataSnapshot.child("name").value.toString(),
                        dataSnapshot.child("surname").value.toString(),
                        dataSnapshot.child("avatar").value.toString(),
                        dataSnapshot.child("status").value.toString(),
                        liked)
                    AuthUser.setInstance(curUser)

                    Log.d("SETUPINFO", "User created")
                }
            })
        }
    }
}