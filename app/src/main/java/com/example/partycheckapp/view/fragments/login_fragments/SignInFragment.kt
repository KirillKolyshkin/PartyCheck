package com.example.partycheckapp.view.fragments.login_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.partycheckapp.R
import com.example.partycheckapp.view.BottomNavActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment: MvpAppCompatFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            btn_sign_in.setOnClickListener {enterApp()}
        }
    }

    fun enterApp(){
        startActivity(Intent(context, BottomNavActivity::class.java))
    }

    companion object {
        fun newInstance(): SignInFragment {
            val args = Bundle()
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }
    }
}