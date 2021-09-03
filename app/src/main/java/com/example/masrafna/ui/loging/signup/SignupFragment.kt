package com.example.masrafna.ui.loging.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.request.SignUpBody
import com.example.masrafna.data.auth.response.SignupResponse
import com.example.masrafna.databinding.FragmentSignupBinding
import com.example.masrafna.ui.loging.LoggingActivity
import com.example.masrafna.util.Session
import com.google.android.material.snackbar.Snackbar


private const val TAG = "SignupFragment myTag"

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val mainInterface = MainAPIClient.getClient()
    private val signupViewModel: SignupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        binding.createBtn.setOnClickListener {
            if (chekInputs())
                signup()
        }

        binding.haveAccountTv.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_signup_to_nav_loginFragment
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupViewModel.networkStatus.observe(this,
            {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE

                if (it.status == Status.CODE_422) {
                    Snackbar.make(
                        binding.root,
                        it.msg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }

            })

        signupViewModel.signUpResponse.observe(this,
            {
                updateUi(it)
            })


    }


    //SignupResponse
    private fun signup() {

        val signUpModel = SignUpBody(
            name = binding.nameEt.text.toString()
                .trim(),
            phone = binding.phoneNoEt.text.toString().trim(),
            password = binding.passwordEt.text.toString().trim()
        )
        signupViewModel.signup(signUpModel)


    }


    private fun updateUi(response: SignupResponse?) {
        Log.d(TAG, "updateUi: ")

        if (response != null) {
            if (!response.error) {
                saveToken(response.payload.accessToken.token)

                Session.signupResponse = response
            }
        }

    }


    private fun saveToken(accessToken: String) {

        Session.token = accessToken
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.access_token_preferences), Context.MODE_PRIVATE
        ) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.access_token), accessToken)
            apply()
        }

        findNavController().navigate(R.id.action_nav_signup_to_nav_confirmCodeFragment)


    }


    /**
     * checks if the inputs are not empty
     * and the tow passwords are the same
     */
    private fun chekInputs(): Boolean {
        with(binding) {
            if (
                nameEt.text.toString().isEmpty() ||
                phoneNoEt.text.toString().isEmpty() ||
                passwordEt.text.toString().isEmpty() ||
                confirmPasswordEt.text.toString().isEmpty()
            ) {
                Snackbar.make(root, "الرجاء التحقق من المدخلات", Snackbar.LENGTH_LONG)
                    .show()
                return false
            }

            if (passwordEt.text.toString() != confirmPasswordEt.text.toString()) {

                Snackbar.make(
                    binding.root,
                    "الرجاء التحقق من تطابق كلمتي المرور",
                    Snackbar.LENGTH_LONG
                ).show()
                return false
            } else {
                if (passwordEt.text.toString().trim().length < 8)
                    Snackbar.make(binding.root, "كلمة السر 8 أحرف على الأقل", Snackbar.LENGTH_LONG)
                        .show()
            }

            if (!agreeCheck.isChecked) {
                Snackbar.make(binding.root, "الرجاء الموافقة على الشروط", Snackbar.LENGTH_LONG)
                    .show()
                return false

            }

            return true
        }


    }


}