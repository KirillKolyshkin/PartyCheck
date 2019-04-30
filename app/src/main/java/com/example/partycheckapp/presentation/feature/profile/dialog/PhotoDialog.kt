package com.example.partycheckapp.presentation.feature.profile.dialog

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.partycheckapp.R
import kotlinx.android.synthetic.main.dialog_choise_pict.*
import kotlinx.android.synthetic.main.dialog_ver_code.*
import android.provider.MediaStore





class PhotoDialog: DialogFragment(), View.OnClickListener {

    val LOG_TAG = "myLogs"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_choise_pict, container)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_photo.setOnClickListener(this)
        btn_gallery.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btn_photo ->{
                sendResult(228, 0)
                dismiss()
            }
            btn_gallery ->{
                sendResult(228, 1)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        Log.d(LOG_TAG, "Dialog 1: onCancel")
    }


    private fun sendResult(REQUEST_CODE: Int, option: Int) {
        val intent = Intent()
        intent.putExtra("option", option)
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }
}