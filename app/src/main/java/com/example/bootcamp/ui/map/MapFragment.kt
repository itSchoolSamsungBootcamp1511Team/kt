package com.example.bootcamp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bootcamp.R
import com.example.bootcamp.dataClasses.AuthUser

class MapFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val textView: TextView = root.findViewById(R.id.text_map)
        Toast.makeText(context, AuthUser.getInstance().toString(), Toast.LENGTH_LONG).show()
        return root
    }
}