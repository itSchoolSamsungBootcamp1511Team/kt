package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.*
import com.example.bootcamp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            binding.layout.setTransition(R.id.end, R.id.end_end)
            binding.layout.setTransitionDuration(1000)
            binding.layout.transitionToEnd()

            Handler().postDelayed({
                val intent = Intent(this, SplashScreen::class.java)
                intent.putExtra("228", 1)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }, 2000)
        }

        binding.signInButton.setOnClickListener {
            Toast.makeText(this, "Sign-in!", Toast.LENGTH_SHORT).show()
        }
    }
}