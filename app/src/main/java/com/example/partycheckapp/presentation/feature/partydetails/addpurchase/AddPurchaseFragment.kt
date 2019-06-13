package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.data.user.UserWithFlag
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListFragment
import kotlinx.android.synthetic.main.fragment_add_purchase.*
import java.io.File
import javax.inject.Inject

class AddPurchaseFragment : MvpAppCompatFragment(), AddPurchaseView {
    @Inject
    @InjectPresenter
    lateinit var addPurchasePresenter: AddPurchasePresenter

    @ProvidePresenter
    fun initPresenter() = addPurchasePresenter

    private val addPurchaseAdapter = AddPurchaseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .partyDetailsComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_add_purchase, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        recycler_view.adapter = addPurchaseAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        initClickListeners()
        initToolbar()
        initTextListeners()
        arguments?.getString("party_id")?.let {
            addPurchasePresenter.getParty(it)
        }
    }

    private fun initToolbar() {
        val activity = (activity as PartyDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.add_purchase)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    private fun initClickListeners() {
        iv_photo.setOnClickListener {
            showDialog(true)
        }
        iv_check.setOnClickListener {
            showDialog(false)
        }
        btn_accept.setOnClickListener {
            val title = et_title.text.toString()
            val price = et_price.text.toString()

            if (title.isEmpty()) {
                ti_title.error = getString(R.string.error_title)
                return@setOnClickListener
            }
            if (price.isEmpty()) {
                ti_price.error = getString(R.string.error_price)
                return@setOnClickListener
            }

            var bitmap: Bitmap?
            try {
                bitmap = (iv_photo.drawable as BitmapDrawable).bitmap
            } catch (e: Exception) {
                bitmap = null
            }

            var bitmap2: Bitmap?
            try {
                bitmap2 = (iv_check.drawable as BitmapDrawable).bitmap
            } catch (e: Exception) {
                bitmap2 = null
            }
            if (bitmap != null && bitmap2 != null) {
                val users = addPurchaseAdapter.list
                val chosenUsers = ArrayList<User>()
                for (user in users)
                    if (user.flag)
                        chosenUsers.add(parseFromUSerWithFlagTOUser(user))

                val partyId = arguments?.getString("party_id") ?: ""
                addPurchasePresenter.addPurchase(partyId, title, price.toDouble(), chosenUsers, bitmap, bitmap2)

                fragmentManager?.beginTransaction()?.replace(R.id.container, PurchaseListFragment.newInstance(partyId))
                    ?.commit()
            } else {
                Toast.makeText(context, "Please, add photos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseFromUSerWithFlagTOUser(userWithFlag: UserWithFlag): User {
        return User(userWithFlag.name, userWithFlag.phoneNumber, userWithFlag.cardNumber, userWithFlag.imageUrl)
    }

    private fun parseFromUserToUserWithFlag(user: User): UserWithFlag {
        return UserWithFlag(user.name, user.phoneNumber, user.cardNumber, user.imageUrl, false)
    }

    override fun getParty(party: Party) {
        val users = party.users
        val usersWithFlag = ArrayList<UserWithFlag>()
        for (user in users)
            usersWithFlag.add(parseFromUserToUserWithFlag(user))
        addPurchaseAdapter.list = usersWithFlag
        addPurchaseAdapter.notifyDataSetChanged()
    }

    override fun showDialog(isIcon: Boolean) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.choose_type))
                .setPositiveButton("Camera") { _, _ -> takePhoto(isIcon) }
                .setNegativeButton("Gallery") { _, _ -> chooseFromDevise(isIcon) }
                .show()
        }
    }

    private fun takePhoto(isIcon: Boolean) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (isIcon)
            startActivityForResult(cameraIntent, TAKE_PICTURE)
        else
            startActivityForResult(cameraIntent, TAKE_PICTURE_CHECK)
    }

    private fun chooseFromDevise(isIcon: Boolean) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        if (isIcon)
            startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                REQUEST_GET_SINGLE_FILE
            )
        else
            startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                REQUEST_GET_SINGLE_FILE_CHECK
            )
    }

    private fun initTextListeners() {
        et_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_title.error = null
            }
        })
        et_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                ti_price.error = null
            }
        })
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
            101 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val selectedImage =
                        data?.extras?.get("data") as Bitmap
                    iv_check.setImageBitmap(selectedImage)
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
            201 -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    var selectedImageUri = data?.data
                    val path = selectedImageUri?.let { getPathFromURI(it) }
                    if (path != null) {
                        val f = File(path)
                        selectedImageUri = Uri.fromFile(f)
                    }
                    iv_check.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.contentResolver?.query(contentUri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(columnIndex)
            }
        }
        cursor?.close()
        return res
    }

    companion object {
        private const val TAKE_PICTURE = 100
        private const val REQUEST_GET_SINGLE_FILE = 200
        private const val TAKE_PICTURE_CHECK = 101
        private const val REQUEST_GET_SINGLE_FILE_CHECK = 201
        fun newInstance(partyId: String): AddPurchaseFragment {
            val args = Bundle()
            args.putString("party_id", partyId)
            val fragment = AddPurchaseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
