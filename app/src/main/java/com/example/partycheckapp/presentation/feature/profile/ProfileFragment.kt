package com.example.partycheckapp.presentation.feature.profile

import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.partycheckapp.R
import kotlinx.android.synthetic.main.fragment_profile.*
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.data.user.User
import javax.inject.Inject
import android.net.Uri
import java.io.File
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProfileFragment : MvpAppCompatFragment(),
    ProfileView {

    @Inject
    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    @ProvidePresenter
    fun initPresenter() = profilePresenter

    private var editFieldsMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFocusable()
        if (savedInstanceState == null) {
            ib_edit.setOnClickListener { changeFocusable() }
        }
    }

    private fun initFocusable() {
        et_phone_num.tag = et_phone_num.keyListener
        et_card_num.tag = et_card_num.keyListener
        editFieldsMode = false
        et_phone_num.keyListener = null
        et_card_num.keyListener = null

    }

    override fun setUser(user: User?) {
        et_phone_num.setText(user?.phoneNumber)
        tv_user_name.text = user?.name
        et_card_num.setText(user?.cardNumber.toString())
        Picasso.with(context).load(user?.imageUrl).into(iv_photo)
    }

    private fun changeFocusable() {
        editFieldsMode = !editFieldsMode

        if (editFieldsMode) {
            ib_edit.setImageResource(R.drawable.ic_save_black_24dp)
            et_phone_num.keyListener = et_phone_num.tag as KeyListener?
            et_card_num.keyListener = et_card_num.tag as KeyListener?
            iv_photo.setOnClickListener { showDialog() }
        } else if (!editFieldsMode) {
            ib_edit.setImageResource(R.drawable.ic_mode_edit_black_24dp)
            updateUser()
            et_phone_num.keyListener = null
            et_card_num.keyListener = null
            iv_photo.setOnClickListener { }
            view?.let { it.hideKeyboardFrom() }
        }
    }

    override fun showDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.choose_type))
                .setPositiveButton("Camera") { _, _ -> takePhoto() }
                .setNegativeButton(
                    "Gallery"
                ) { _, _ -> chooseFromDevise() }
                .show()
        }
    }

    private fun takePhoto() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            cameraIntent,
            TAKE_PICTURE
        )
    }

    private fun chooseFromDevise() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.select_picture)),
            REQUEST_GET_SINGLE_FILE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == RESULT_OK) {
                    val selectedImage =
                        data?.extras?.get("data") as Bitmap
                    iv_photo.setImageBitmap(selectedImage)
                }
            }
            200 -> {
                if (resultCode == RESULT_OK) {
                    var selectedImageUri = data?.data
                    val path = selectedImageUri?.let { getPathFromURI(it) }
                    if (path != null) {
                        val f = File(path)
                        selectedImageUri = Uri.fromFile(f)
                    }
                    iv_photo.setImageURI(selectedImageUri)
                }
            }
        }
    }


    private fun View.hideKeyboardFrom() {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            this.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.let { it.contentResolver.query(contentUri, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(columnIndex)
            }
        }
        cursor?.close()
        return res
    }

    private fun updateUser() {
        val name = tv_user_name.text.toString()
        val phone = et_phone_num.text.toString().trim { it <= ' ' }
        var card: Long = 0
        try {
            card = et_card_num.text.toString().trim { it <= ' ' }.toLong()
        } catch (e: Exception) {
        }

        var bitmap: Bitmap?
        try {
            bitmap = (iv_photo.drawable as BitmapDrawable).bitmap
        } catch (e: Exception) {
            bitmap = null
        }
        profilePresenter.updateUser(name, phone, card.toString(), bitmap)
    }

    companion object {
        private const val TAKE_PICTURE = 100
        private const val REQUEST_GET_SINGLE_FILE = 200
        fun newInstance(): ProfileFragment =
            ProfileFragment()
    }
}
