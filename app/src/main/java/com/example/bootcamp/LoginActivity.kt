package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.dataClasses.User
import com.example.bootcamp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val listPosts = Post.createTestList()

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
            fillUser();
            /* just for test */
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            Toast.makeText(this, "Sign-in!", Toast.LENGTH_SHORT).show()
        }
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
                    val postsID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("posts").children) {
                        postsID.add(snap.value.toString().toInt());
                    }

                    val likesID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("likes").children) {
                        likesID.add(snap.value.toString().toInt());
                    }

                    val commentsID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("comments").children) {
                        commentsID.add(snap.value.toString().toInt());
                    }
                    val curUser = User(dataSnapshot.child("name").value.toString(),
                        dataSnapshot.child("surname").value.toString(),
                        dataSnapshot.child("avatar").value.toString(),
                        dataSnapshot.child("status").value.toString(),
                        dataSnapshot.child("otherMeLikes").value.toString().toInt(),
                        myId, postsID, likesID, commentsID)
                    AuthUser.setInstance(curUser)

                    Log.e("LoginTag", "User created")
                }
            })
        }
    }
}