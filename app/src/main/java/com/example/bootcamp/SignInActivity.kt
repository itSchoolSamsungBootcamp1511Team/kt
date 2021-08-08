package com.example.bootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bootcamp.Utils.Companion.checkEmail
import com.example.bootcamp.databinding.ActivitySignInBinding
import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager


class SignInActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySignInBinding

    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                0
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            val login = binding.loginText.text.toString()
            val pass = binding.passwordText.text.toString()

            if(login != "" && pass != "") {
                if(checkEmail(login)) {
                    hideSoftKeyboard()
                    binding.layout.setTransition(R.id.end, R.id.end_end)
                    binding.layout.setTransitionDuration(1000)
                    binding.layout.transitionToEnd()

                    Handler().postDelayed({
                        val intent = Intent(this, SplashScreen::class.java)
                        intent.putExtra("statusLogin", 1)
                        intent.putExtra("loginUser", login)
                        intent.putExtra("passwordUser", pass)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }, 2000)
                } else {
                    Toast.makeText(this, "Email in format 'name@host.domain'", Toast.LENGTH_SHORT).show()
                    resetFields()
                }
            } else {
                val text = "Not enter '" + when {
                    login == "" && pass == "" -> "all fields"
                    login == "" -> getString(R.string.email_login)
                    pass == "" -> getString(R.string.password)
                    else -> "SOMETHING STRANGE!"
                } + "'"
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                resetFields()
            }
        }

        binding.signInButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    private fun resetFields(){
        binding.loginText.setText("")
        binding.passwordText.setText("")
    }
}