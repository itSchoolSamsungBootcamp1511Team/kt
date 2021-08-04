package com.example.bootcamp.accountfragmentui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.databinding.FragmentAccountBinding
import com.example.bootcamp.databinding.Viewpager2FeedBinding


class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)


        return binding.root
    }


}