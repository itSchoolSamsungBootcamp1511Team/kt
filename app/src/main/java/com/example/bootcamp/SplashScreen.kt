package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.User
import com.example.bootcamp.databinding.ActivitySplashScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)

        val statusLogin = intent.getIntExtra("228", 0)
        when(statusLogin){
            0 -> {
                Handler().postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                }, 1000)
            }
            1 -> {

                fillUser()
                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                }, 3000)
            }
        }

        setContentView(binding.root)
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
//                    for (snap in dataSnapshot.child("posts").children) {
//                        postsID.add(snap.value.toString().toInt());
//                    }

                    val likesID = ArrayList<Int>()
//                    for (snap in dataSnapshot.child("likes").children) {
//                        likesID.add(snap.value.toString().toInt());
//                    }

                    val commentsID = ArrayList<Int>()
//                    for (snap in dataSnapshot.child("comments").children) {
//                        commentsID.add(snap.value.toString().toInt());
//                    }
                    val curUser = User(myId,
                        dataSnapshot.child("name").value.toString(),
                        dataSnapshot.child("surname").value.toString(),
                        dataSnapshot.child("avatar").value.toString(),
                        dataSnapshot.child("status").value.toString(),
                        dataSnapshot.child("otherMeLikes").value.toString().toInt(),
                        postsID, likesID, commentsID)
                    AuthUser.setInstance(curUser)

                    Log.e("LoginTag", "User created")
                }
            })
        }
    }
}