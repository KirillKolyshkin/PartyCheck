package com.example.partycheckapp.presentation.feature.auth.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.auth.dialog.CodeDialog
import com.example.partycheckapp.presentation.feature.auth.presenter.SignInPresenter
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : SignInView, MvpAppCompatFragment() {

    @Inject
    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    @ProvidePresenter
    fun initPresenter() = signInPresenter

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp
            .instance
            .getAppComponent()
            .dateComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()
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
            val phone = et_phone.text.toString().trim { it <= ' ' }
            val login = et_login.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(phone)) {
                ti_phone.error = getString(R.string.error_phone)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(login)){
                ti_login.error = getString(R.string.error_login)
                return@setOnClickListener
            }
            signInPresenter.sentCode(phone)

           }
    }

    override fun showDialog() {
        val dlg1 = CodeDialog()
        dlg1.setTargetFragment(this, 228)
        dlg1.show(fragmentManager, "dlg1")
    }

    override fun checkUser() {
        var curUser = signInPresenter.getUser()
        if (curUser != null) {
            enterTheApp()
        } else {
        }
    }

    override fun confirmAuth() {
        val login = et_login.text.toString().trim { it <= ' ' }
        val phone = et_phone.text.toString().trim { it <= ' ' }
        signInPresenter.addUserToDB(login, phone)
        enterTheApp()
    }

    fun enterTheApp(){
        startActivity(Intent(context, MainActivity::class.java))
    }

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }
}
