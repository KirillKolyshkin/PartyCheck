package com.example.partycheckapp.view.fragments.bottom_nav_fragments

import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.partycheckapp.R
import kotlinx.android.synthetic.main.fragment_profile.*
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


class ProfileFragment : MvpAppCompatFragment() {

    private var editFieldsMode = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        et_phone_num.tag = et_phone_num.keyListener
        et_card_num.tag = et_card_num.keyListener
        changeFocusable()

        if (savedInstanceState == null) {
            ib_edit.setOnClickListener { changeFocusable() }
        }
    }

    fun changeFocusable() {
        editFieldsMode = !editFieldsMode

        when (editFieldsMode) {
            true -> {
                et_phone_num.keyListener = et_phone_num.tag as KeyListener?
                et_card_num.keyListener = et_card_num.tag as KeyListener?
            }
            false -> {
                et_phone_num.keyListener = null
                et_card_num.keyListener = null
                this.context?.let { view?.let { it1 -> hideKeyboardFrom(it, it1) } }
            }
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}