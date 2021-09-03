package com.example.masrafna.ui.navigation.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.masrafna.R
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.databinding.FragmentProfileBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.util.Cities
import com.example.masrafna.util.FileFromUri
import com.example.masrafna.util.Session
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "ProfileFragment myTag"

class ProfileFragment : Fragment() {
    private var name = ""
    private var email = ""
    private var phoneNO = ""
    private var dOB = ""
    private var address = ""
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val PICK_IMAGE_MULTIPLE: Int = 1
    private var file: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        getData()
        setTextFields()

        binding.editImageBtn.setOnClickListener {
            choseImage()
        }
        profileViewModel.getProfile()
    }

    private fun choseImage() {
        val intent = Intent()
        intent.type = "image/*"

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_MULTIPLE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && data != null) {
            file = FileFromUri.getFile(requireContext(), data.data!!)
            Glide.with(requireContext())
                .load(data.data)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(50, 0)))
                .into(binding.imageProfile)

            binding.saveChangesBtn.visibility = VISIBLE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(profileViewModel) {
            networkStatus.observe(this@ProfileFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE

                if (it.status == Status.CODE_422) {
                    Snackbar.make(
                        binding.root,
                        it.msg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else if (it.status == Status.CODE_401) {

                    /**
                     *  this means that the token is invalid and the user have to login again
                     *  to get new valid token.
                     */

                    Log.e(TAG, "onCreate: ${it.msg}")
                }
            })

            updateProfileResponse.observe(this@ProfileFragment, {
                if (it != null) {
                    onDataSaved(it)
                }
            })
            profileResponse.observe(this@ProfileFragment, {
                Session.profileResponse = it
                getData()
            })
        }
    }

    private fun setupToolbar() {

        with(binding) {
            toolbar.drawerIcon.setOnClickListener {
                (requireContext() as NavigationDrawerActivity)
                    .binding.drawerLayout.open()
            }
            toolbar.navigateUp.setOnClickListener {
                findNavController().navigateUp()
            }

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    /**
     * request data from the server and populate it to ui.
     */
    private fun getData() {

        with(binding) {
            if (Session.profileResponse != null)
                with(Session.profileResponse!!.payload) {
                    nameEt.setText(name)
                    emailEt.setText(email)
                    phoneNoEt.setText(phone)
                    dobEt.setText(birth)
                    Glide.with(requireContext())
                        .load(image)
                        .placeholder(R.drawable.sticker)
                        .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(50, 0)))
                        .into(imageProfile)


                    this@ProfileFragment.name = name
                    this@ProfileFragment.phoneNO = phone
                    this@ProfileFragment.dOB = birth
                    this@ProfileFragment.email = email
                    this@ProfileFragment.address = address

                    setupSpinner()

                }
            else profileViewModel.getProfile()
        }
    }


/**
 * set listeners to text fields for enable editing and FAB for save changes
 */
private fun setTextFields() {


    with(binding) {

        editDobBtn.setOnClickListener {
            editDOB()
            saveChangesBtn.visibility = VISIBLE
        }
        editNameBtn.setOnClickListener {
            nameEt.isEnabled = true
            saveChangesBtn.visibility = VISIBLE
        }
        editEmailBtn.setOnClickListener {
            emailEt.isEnabled = true
            saveChangesBtn.visibility = VISIBLE
        }
//            editPhoneBtn.setOnClickListener {
//                phoneNoEt.isEnabled = true
//                saveChangesBtn.visibility = VISIBLE
//            }

        saveChangesBtn.setOnClickListener {
            checkChangesAndSave()
        }
    }
}


/**
 * show date picker dialog for change the date of birth of the user
 */
private fun editDOB() {

    val c = Calendar.getInstance()
    val mYear = c.get(Calendar.YEAR)
    val mMonth = c.get(Calendar.MONTH)
    val mDay = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        requireContext(),
        { _, year, month, dayOfMonth ->

            binding.dobEt.setText("$year-${month + 1}-$dayOfMonth")

        }, mYear, mMonth, mDay
    )
    datePickerDialog.show()
}

/**
 * check if any changes happened for data and if so, update the user profile.
 * otherwise ignore.
 *
 */
private fun checkChangesAndSave() {
    val newName = binding.nameEt.text.toString()
    val newEmail = binding.emailEt.text.toString()
    val newPhoneNO = binding.phoneNoEt.text.toString()
    val newDOB = binding.dobEt.text.toString()
    val newAddress = binding.citySpinner.selectedItem.toString()

    savData(
        newName = newName,
        newPhoneNO = newPhoneNO,
        newDOB = newDOB,
        newEmail = newEmail,
        newAddress = newAddress
    )

}

/**
 * update user`s data to the server
 */
private fun savData(
    newName: String,
    newEmail: String,
    newPhoneNO: String,
    newDOB: String,
    newAddress: String
) {
    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    builder
        .addFormDataPart("address", newAddress)
        .addFormDataPart("email", newEmail)
        .addFormDataPart("name", newName)
        .addFormDataPart("birth", newDOB)
    if (file != null) {
        builder.addFormDataPart(
            "image", file?.name,
            RequestBody.create(
                MediaType.parse("application/octet-stream"),
                file
            )
        )
    }
    val body = builder.build()

    profileViewModel.updateProfile(body)

}

/**
 * listener for completed updating to populate the new data to ui.
 * also disable editing in the text fields.
 */
private fun onDataSaved(response: ProfileResponse) {
    Log.d(TAG, "onDataSaved:")
    binding.saveChangesBtn.visibility = GONE
    binding.phoneNoEt.isEnabled = false
    binding.nameEt.isEnabled = false
    binding.emailEt.isEnabled = false
    binding.dobEt.isEnabled = false

    Session.profileResponse = response


}


/**
 * setup the spinner to show user`s city and get all cities from the server.
 * show all cities to the spinner.
 */
private fun setupSpinner() {
    val citiesNamed = ArrayList<String>()
    for (city in Cities.getCities()) {
        citiesNamed.add(city.name!!)
    }


    val spinner = binding.citySpinner

    // Create an ArrayAdapter using the string array and a default spinner layout

    val cityListAdapter: ArrayAdapter<String> = ArrayAdapter(
        requireActivity(),
        R.layout.my_spinner_background, citiesNamed
    )

    cityListAdapter.setDropDownViewResource(R.layout.my_spinner_item)

    // Apply the adapter to the spinner
    spinner.adapter = cityListAdapter

    var index = citiesNamed.indexOf(Session.profileResponse!!.payload.address)

    Log.d(TAG, "setupSpinner: $index")
    if (index < 0)
        index = 0

    spinner.setSelection(index)

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (address != citiesNamed[position]) {
                binding.saveChangesBtn.visibility = VISIBLE

            }

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }


}

}