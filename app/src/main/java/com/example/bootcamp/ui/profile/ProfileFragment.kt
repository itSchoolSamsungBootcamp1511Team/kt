package com.example.bootcamp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bootcamp.R
import com.example.bootcamp.dataClasses.AuthUser
import com.example.bootcamp.dataClasses.User

class ProfileFragment : Fragment() {

    private lateinit var nameSurnameText: TextView;
    private lateinit var statusText: TextView;
    private lateinit var postsAmountText: TextView;
    private lateinit var likesAmountText: TextView;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        nameSurnameText = root.findViewById(R.id.nameSurnameText)
        statusText = root.findViewById(R.id.statusText)
        postsAmountText = root.findViewById(R.id.PostsAmountText)
        likesAmountText = root.findViewById(R.id.LikesTitleText)
        drawIU()
        return root
    }

    private fun drawIU() {
        if (AuthUser.getInstance() == null) {
            return;
        }
        var chel = AuthUser.getInstance()!!;
        nameSurnameText.setText(chel.name + " " + chel.surname);
        statusText.setText(chel.status)
        postsAmountText.setText(chel.myPosts.size.toString())
        likesAmountText.setText(chel.otherMeLikes.toString())


    }

}