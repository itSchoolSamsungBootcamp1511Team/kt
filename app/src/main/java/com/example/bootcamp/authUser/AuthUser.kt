package com.example.bootcamp.authUser

import android.content.SharedPreferences
import android.util.Log
import com.example.bootcamp.dataClasses.User

class AuthUser(){
    companion object {
        private var user: User? = null

        fun getInstance(): User? {
            return user
        }

        fun setUser228(user228: User) {
            user = user228
        }
    }
}