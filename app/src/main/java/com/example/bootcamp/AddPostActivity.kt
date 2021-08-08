package com.example.bootcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bootcamp.databinding.ActivityAddPostBinding
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.Post
import com.example.bootcamp.dataClasses.PostBase
import com.example.bootcamp.dataClasses.UserBase
import com.google.firebase.database.FirebaseDatabase


class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding

    private fun setupWindow(){
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(this.layoutInflater)
        setupWindow()
        setContentView(binding.root)

        binding.addPhotoButton.setOnClickListener {
            Toast.makeText(this, "Add photo in develop", Toast.LENGTH_SHORT).show()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.publishButton.setOnClickListener {
            val text = binding.postText.text.toString()
            if(text == ""){
                Toast.makeText(this, "You post must have text", Toast.LENGTH_SHORT).show()
            } else {
                val time = System.currentTimeMillis()
                addPost(text, time, AuthUser.getInstance()!!.id)
                backIntent()
            }
        }
    }

    private fun backIntent(){
        val intent = Intent(this@AddPostActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun addPost(text: String, time: Long, userId: String){
        class Postik(var text: String, var time: Long, var userUID: String) {

        }
        val postik = Postik(text, time, userId)
        FirebaseDatabase.getInstance().reference
            .child("posts").push().setValue(postik)
        Utils.fillPosts()

    }
}