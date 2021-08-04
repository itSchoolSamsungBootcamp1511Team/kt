package com.example.bootcamp.dataClasses

class UserBase {
    companion object {
        private var currentUsers: ArrayList<User>? = null

        fun getInstance(): ArrayList<User>? {
            return currentUsers
        }

        fun setInstance(users: ArrayList<User>) {
            currentUsers = users
        }
    }
}