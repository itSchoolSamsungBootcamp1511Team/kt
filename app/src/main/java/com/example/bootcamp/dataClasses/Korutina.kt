package com.example.bootcamp.dataClasses

import android.content.Context
import android.os.AsyncTask
import com.google.firebase.database.FirebaseDatabase


class Korutina():
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            FirebaseDatabase.getInstance().reference.child("users")
                .child(AuthUser.getInstance()!!.id)
                .child("likedPostsId").setValue(AuthUser.getInstance()!!.likedPostsId)
            return null
        }
}