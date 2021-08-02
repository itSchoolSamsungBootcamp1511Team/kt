package com.example.bootcamp.ui.map

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FirebasePoint : Thread() {

    companion object {
        private const val TAG = "KotlinActivity"
    }

    private var mDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("/map")
    private var points: List<Point> = ArrayList()


    public fun addNewPoint() = runBlocking{
        Log.wtf(TAG, "YA TUT AAAA")

 //       launch {
        //Read from the database
            mDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    var value = dataSnapshot.getValue<ArrayList<HashMap<String, String>>>()

                    var buff : Point? = value?.get(1)?.let {
                        Point(
                            value?.get(1)?.getValue("id").toInt() ,
                            it.getValue("name") ,
                            value?.get(1)?.getValue("latitude").toDouble() ,
                            value?.get(1)?.getValue("longitude").toDouble())
                    }
                    if(buff != null) {
                        points += buff as Point
                        Log.wtf(TAG, "CHECK: ${points.first()}")
                    }


                    Log.wtf(TAG, "Value is: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.wtf(TAG, "Failed to read value.", error.toException())
                }
            })


            // [END read_message]
 //       }
    }


}