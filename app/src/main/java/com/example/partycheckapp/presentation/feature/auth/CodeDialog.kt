package com.example.partycheckapp.presentation.feature.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.partycheckapp.R
import kotlinx.android.synthetic.main.dialog_ver_code.*

class CodeDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_ver_code, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_accept.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btn_accept -> {
                val password = et_password.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(password)) {
                    ti_password.error = getString(R.string.error_pass)
                } else {
                    sendResult(228)
                    dismiss()
                }
            }
        }
    }

    private fun sendResult(REQUEST_CODE: Int) {
        val intent = Intent()
        val password = et_password.text.toString().trim { it <= ' ' }
        intent.putExtra("VERIFICATION_CODE", password)
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }
}
