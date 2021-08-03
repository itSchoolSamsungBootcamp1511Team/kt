package com.example.bootcamp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bootcamp.databinding.ActivityMainBinding
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupWindow()
        setContentView(binding.root)
        setupBottomNavigation()
        fillUser();
    }

    private fun setupWindow(){
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
    }

    private fun setupBottomNavigation(){
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_map,
            R.id.navigation_feed,
            R.id.navigation_account
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

    }

    private fun fillUser() {
        var myId = intent.getIntExtra("UserId", 1);
        var now = AuthUser.getInstance()
        if (myId == -1 && now == null) {
            Toast.makeText(applicationContext, "Что-то пошло не так!", Toast.LENGTH_LONG).show();
            this.finish();
        }
        if (now == null) {
            val myBase = FirebaseDatabase.getInstance().getReference();
            myBase.child("users").child(myId.toString()).addValueEventListener(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var postsID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("posts").children) {
                        postsID.add(snap.getValue().toString().toInt());
                    }

                    var likesID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("likes").children) {
                        likesID.add(snap.getValue().toString().toInt());
                    }

                    var commentsID = ArrayList<Int>()
                    for (snap in dataSnapshot.child("comments").children) {
                        commentsID.add(snap.getValue().toString().toInt());
                    }
                    val curUser = User(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("surname").getValue().toString(),
                            dataSnapshot.child("avatar").getValue().toString(),
                            dataSnapshot.child("status").getValue().toString(),
                            dataSnapshot.child("otherMeLikes").getValue().toString().toInt(),
                            myId, postsID, likesID, commentsID)
                    AuthUser.setUser228(curUser)
                }

            })
        }
    }
}