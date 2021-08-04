package com.example.bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bootcamp.dataClasses.AuthUser


class AccountFragment : Fragment() {
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
        likesAmountText = root.findViewById(R.id.LikesAmountText)
        drawIU()
        return root
    }

    private fun drawIU() {
        if (AuthUser.getInstance() == null) {
            return;
        }
        val chel = AuthUser.getInstance()!!
        nameSurnameText.text = chel.name + " " + chel.surname;
        statusText.text = chel.status
        postsAmountText.text = chel.myPostsId.size.toString()
        likesAmountText.text = chel.otherMeLikes.toString()
    }


}