package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.*
import com.example.bootcamp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            /* just for test */
            fillInfo()
            /* just for test */
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            Toast.makeText(this, "Sign-in!", Toast.LENGTH_SHORT).show()
        }
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
                    users.add(User(
                        i.key.toString().toInt(),
                        i.child("name").value.toString(),
                        i.child("surname").value.toString(),
                        i.child("avatar").value.toString(),
                        i.child("status").value.toString(),
                    ))
                }
                UserBase.setInstance(users)
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
                    posts.add(Post(
                        i.key.toString().toInt(),
                        i.child("userId").value.toString().toInt(),
                        i.child("time").value.toString().toLong(),
                        i.child("photos").value.toString(),
                        i.child("data").value.toString(),
                        i.child("likes").value.toString().toInt(),
                    ))
                }
                PostBase.setInstance(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fillUser() {
        val myId = intent.getIntExtra("UserId", 1);
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
                    val curUser = User(myId,
                        dataSnapshot.child("name").value.toString(),
                        dataSnapshot.child("surname").value.toString(),
                        dataSnapshot.child("avatar").value.toString(),
                        dataSnapshot.child("status").value.toString())
                    AuthUser.setInstance(curUser)

                    Log.d("SETUPINFO", "User created")
                }
            })
        }
    }
}