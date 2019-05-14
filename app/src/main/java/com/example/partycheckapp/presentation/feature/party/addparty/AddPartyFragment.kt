package com.example.partycheckapp.presentation.feature.party.addparty

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_add_party.*
import java.io.File
import java.util.*
import javax.inject.Inject

class AddPartyFragment : MvpAppCompatFragment(), AddPartyView {

    @Inject
    @InjectPresenter
    lateinit var addPartyPresenter: AddPartyPresenter

    @ProvidePresenter
    fun initPresenter() = addPartyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .addPartyComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_add_party, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initClickListeners()
        initTextListeners()
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

    fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, TAKE_PICTURE)
    }

    fun chooseFromDevise() {
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
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val selectedImage =
                        data?.extras?.get("data") as Bitmap
                    iv_photo.setImageBitmap(selectedImage)
                }
            }
            200 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
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

    private fun initClickListeners() {
        iv_photo.setOnClickListener {
            showDialog()
        }
        btn_accept.setOnClickListener {
            val title = et_title.text.toString()
            val description = et_description.text.toString()
            val place = et_place.text.toString()
            val password = et_password.text.toString()

            val calendar = GregorianCalendar(dp.year, dp.month, dp.dayOfMonth)
            val date = calendar.time

            if (title.isEmpty()) {
                ti_title.error = getString(R.string.error_title)
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                ti_description.error = getString(R.string.error_description)
                return@setOnClickListener
            }
            if (place.isEmpty()) {
                ti_place.error = getString(R.string.error_place)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                ti_password.error = getString(R.string.error_pass)
                return@setOnClickListener
            }

            var bitmap: Bitmap?
            try {
                bitmap = (iv_photo.drawable as BitmapDrawable).bitmap
            } catch (e: Exception) {
                bitmap = null
            }

            addPartyPresenter.addParty(title, description, place, date, password, bitmap)

            startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private fun initToolbar() {
        val activity = (activity as AddPartyActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.add_party)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    private fun initTextListeners() {
        et_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_title.error = null
            }
        })
        et_description.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_description.error = null
            }
        })
        et_place.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_place.error = null
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_password.error = null
            }
        })
    }

    fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.let { it.contentResolver.query(contentUri, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(column_index)
            }
        }
        cursor?.close()
        return res
    }

    companion object {
        private const val TAKE_PICTURE = 100
        private const val REQUEST_GET_SINGLE_FILE = 200
        fun newInstance(): AddPartyFragment = AddPartyFragment()
    }
}