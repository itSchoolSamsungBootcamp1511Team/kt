package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.Utils.Companion.checkEmail
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.User
import com.example.bootcamp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(this.layoutInflater)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signInButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        val email = binding.loginText.text.toString()
        val pass1 = binding.passwordText.text.toString()
        val pass2 = binding.password2Text.text.toString()
        val userName = binding.userNameText.text.toString()

        val entered = (email != "") && (pass1 != "") && (pass2 != "") && (userName != "")
        if (entered){
            if(pass1.length >= 6) {
                if (pass1 == pass2) {
                    if (checkEmail(email)) {
                        auth.createUserWithEmailAndPassword(email, pass1)
                            .addOnCompleteListener(this) {
                                if (it.exception is FirebaseAuthUserCollisionException) {
                                    Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT)
                                        .show()
                                    resetFields()

                                } else if (it.isSuccessful) {
                                    val user = auth.currentUser?.let { it1 ->
                                        User(it1.uid, userName, "", "", ArrayList())
                                    }
                                    user?.let { it1 ->
                                        FirebaseDatabase.getInstance().reference
                                            .child("users")
                                            .child(it1.id).setValue(user)
                                    }
                                    user?.let { it1 -> AuthUser.setInstance(it1) }

                                    binding.signInLayout.setTransition(
                                        R.id.start_sign,
                                        R.id.end_sign
                                    )
                                    binding.signInLayout.setTransitionDuration(1000)
                                    binding.signInLayout.transitionToEnd()

                                    Handler().postDelayed({
                                        val intent = Intent(this, SplashScreen::class.java)
                                        intent.putExtra("statusLogin", 1)
                                        intent.putExtra("loginUser", email)
                                        intent.putExtra("passwordUser", pass1)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent)
                                        overridePendingTransition(0, 0)
                                        finish()
                                    }, 2000)

                                    Log.d("CreateUserTag", "Created")
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Email in format 'name@host.domain'",
                            Toast.LENGTH_SHORT
                        ).show()
                        resetFields()
                    }
                } else {
                    Toast.makeText(this, "Password must be equal", Toast.LENGTH_SHORT).show()
                    resetFields()
                }
            } else {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                resetFields()
            }
        } else {
            val text = "Not enter '" + when {
                email == "" -> R.string.email_login
                pass1 == "" -> R.string.password
                pass2 == "" -> R.string.repeat_password
                userName == "" -> R.string.user_name
                else -> "all fields"
            } + "'"
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            resetFields()
        }
    }

    private fun isExist(email: String, pass: String): Boolean = auth.signInWithEmailAndPassword(email, pass).isSuccessful

    private fun resetFields(){
        binding.loginText.setText("")
        binding.passwordText.setText("")
        binding.password2Text.setText("")
        binding.userNameText.setText("")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}