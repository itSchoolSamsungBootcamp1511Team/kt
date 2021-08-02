package com.example.bootcamp.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

import com.example.bootcamp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.FirebaseApp


import kotlinx.android.synthetic.main.fragment_map.*;
import kotlinx.coroutines.runBlocking

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    /**  Pair<Name,Coordinates> **/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view: View = inflater!!.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onStart() {
        super.onStart()

        var test : FirebasePoint = FirebasePoint()
        test.addNewPoint()
    }

    override fun onMapReady(googleMap: GoogleMap) = runBlocking{
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)

        //launch
        launch {
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

