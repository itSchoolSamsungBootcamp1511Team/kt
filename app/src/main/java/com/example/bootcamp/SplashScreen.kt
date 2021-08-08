package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.Utils.Companion.fillPosts
import com.example.bootcamp.Utils.Companion.fillUser
import com.example.bootcamp.Utils.Companion.fillUsers
import com.example.bootcamp.Utils.Companion.isOnline
import com.example.bootcamp.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
                Toast.makeText(this, "User don't exist or incorrect password", Toast.LENGTH_SHORT).show()
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
}