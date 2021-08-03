package com.example.bootcamp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.dataClasses.User
import com.example.bootcamp.databinding.ActivityLoginBinding


const val CURRENT_USER_UID = "0"
lateinit var currentUser: User


class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
//            currentUser = User(CURRENT_USER_UID, "Lorem", "Ipsum")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            Toast.makeText(this, "Sign-in!", Toast.LENGTH_SHORT).show()
        }
    }
}