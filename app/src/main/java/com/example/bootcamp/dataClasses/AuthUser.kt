package com.example.bootcamp.dataClasses

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