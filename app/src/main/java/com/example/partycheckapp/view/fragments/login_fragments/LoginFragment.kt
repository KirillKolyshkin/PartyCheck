package com.example.partycheckapp.view.fragments.login_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.partycheckapp.R
import com.example.partycheckapp.view.BottomNavActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_login, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            btn_log_in.setOnClickListener {enterApp()}
                btn_sign_in_offer.setOnClickListener{replaceLoginFragment()}
        }
    }

    fun replaceLoginFragment(){
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .addToBackStack("JoJo")
                .replace(
                    R.id.container,
                    SignInFragment.newInstance()
                )
                .commit()
        }
    }

    fun enterApp(){
        startActivity(Intent(context, BottomNavActivity::class.java))
    }

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }
}