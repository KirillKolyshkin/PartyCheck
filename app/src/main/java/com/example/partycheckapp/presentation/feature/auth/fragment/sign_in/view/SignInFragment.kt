package com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.presenter.SignInPresenter
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : SignInView, MvpAppCompatFragment() {

    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    private var auth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(signInPresenter.tryToPassAuth(activity as Activity))
            enterApp()
        auth = FirebaseAuth.getInstance()

        initClickListeners()
        initTextListeners()
    }


    private fun initTextListeners() {
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                ti_phone.error = null
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                ti_password.error = null
            }
        })
    }


    private fun initClickListeners() {
        btn_get_password.setOnClickListener{
            val phone = et_phone.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(phone)) {
                ti_phone.error = getString(R.string.error_email)
                return@setOnClickListener
            }
            signInPresenter.sentCode (phone, activity as Activity)
        }
        btn_sign_in.setOnClickListener {
            val password = et_password.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(password)) {
                ti_password.error = getString(R.string.error_pass)
                return@setOnClickListener
            }
            if (password.length != 6) {
                ti_password.error = getString(R.string.error_pass_length)
                return@setOnClickListener
            }
            //create user
            signInPresenter.verifyPhone(password, activity as Activity)
            addUser()
        }
    }

    override fun addUser() {
        var curUser = signInPresenter.getUser()
        if (curUser != null) {
            if (curUser.phoneNumber.toString() == et_phone.text.toString()) {
                enterApp()
            } else {
            }

        }
    }

    override fun confirmAuth() {
        enterApp()
    }

    fun enterApp() {
        startActivity(Intent(context, MainActivity::class.java))
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