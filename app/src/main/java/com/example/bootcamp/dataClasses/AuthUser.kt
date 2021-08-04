package com.example.bootcamp.dataClasses

class AuthUser(){
    companion object {
        private var currentUser: User? = null

        fun getInstance(): User? {
            return currentUser
        }

        fun setInstance(user: User) {
            currentUser = user
        }
    }
}