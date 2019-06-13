package com.example.partycheckapp.presentation.feature.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : SignInView, MvpAppCompatFragment() {

    @Inject
    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @ProvidePresenter
    fun initPresenter() = signInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp
            .instance
            .getAppComponent()
            .signInComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()
        initClickListeners()
        initTextListeners()
    }

    private fun initTextListeners() {
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_phone.error = null
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 228) {
            val password = data.getStringExtra("VERIFICATION_CODE")
            signInPresenter.verifyPhone(password)
            checkUser()
        }
    }

    private fun initClickListeners() {
        btn_sign_in.setOnClickListener {
            val phone = et_phone.text.toString()
            val login = et_login.text.toString()
            if (phone.isEmpty()) {
                ti_phone.error = getString(R.string.error_phone)
                return@setOnClickListener
            }
            if (login.isEmpty()) {
                ti_login.error = getString(R.string.error_login)
                return@setOnClickListener
            }
            signInPresenter.sentCode(phone)

        }
    }

    override fun showDialog() {
        val verCodeDialog = CodeDialog()
        verCodeDialog.setTargetFragment(this, 228)
        verCodeDialog.show(fragmentManager, "dlg1")
    }

    override fun checkUser() {
        var curUser = signInPresenter.getUser()
        if (curUser != null) {
            enterTheApp()
        } else {
        }
    }

    override fun confirmAuth() {
        val login = et_login.text.toString()
        val phone = et_phone.text.toString()
        signInPresenter.addUserToDB(login, phone)
        enterTheApp()
    }

    private fun enterTheApp() {
        val activity = (activity as LoginActivity)
        startActivity(Intent(context, MainActivity::class.java))
        activity.finish()
    }

    companion object {
        fun newInstance(): SignInFragment =
            SignInFragment()
    }
}
