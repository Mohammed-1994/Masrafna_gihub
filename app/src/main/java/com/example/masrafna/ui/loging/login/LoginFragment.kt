package com.example.masrafna.ui.loging.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.data.auth.request.LoginBody
import com.example.masrafna.data.auth.response.LoginResponse
import com.example.masrafna.databinding.FragmentLoginBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.util.Session
import com.google.android.material.snackbar.Snackbar

import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.ui.navigation.profile.ProfileViewModel


private const val TAG = "LoginFragment myTag"

class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(loginViewModel) {

            networkStatus.observe(this@LoginFragment, {
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

            loginResponse.observe(this@LoginFragment, {
                if (it != null) {
                    Log.d(TAG, "onCreate: loginResponse")
                    saveToken(it)

                }
            })

            profileResponse.observe(this@LoginFragment, {
                if (it != null) {
                    Log.d(TAG, "onCreate: profile response")
                    /**
                     * this mean that the token is valid and we still need to check if
                     * the OTP is verified.
                     */
                    checkVerified(it)
                }
            })
        }
    }

    private fun saveToken(response: LoginResponse?) {

        Log.d(TAG, "saveToken: ")
        with(Session) {


            loginResponse = response!!

            token = response.payload.accessToken.token
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.access_token_preferences), Context.MODE_PRIVATE
            ) ?: return
            with(sharedPref.edit()) {
                putString(
                    getString(R.string.access_token),
                    response.payload.accessToken.token
                )
                apply()
            }

            loginViewModel.getProfile()
        }
    }

    /**
     *   checks if the user hase verified the OTP.
     *   if verified: enter the app
     *   else: navigate to confirm OTP fragment
     */
    private fun checkVerified(profileResponse: ProfileResponse) {
        Log.d(TAG, "checkVerified: ")
        if (profileResponse.payload.isVerified == 0) {

            findNavController().navigate(R.id.action_nav_login_to_nav_confirmCodeFragment)

        } else if (profileResponse.payload.isVerified == 1) {
            updateUi(profileResponse)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createNewAccount.setOnClickListener {
            //navigate to create new account
            it.findNavController().navigate(R.id.nav_signup)
        }

        checkAuth()
        binding.loginBtn.setOnClickListener {
            if (checkInputs()) {
                login()
            } else
                Snackbar.make(binding.root, "الرجاء التحقق من المدخلات", Snackbar.LENGTH_LONG)
                    .show()

        }

        binding.forgotPassword.setOnClickListener {

            it.findNavController().navigate(R.id.action_nav_login_to_resetPasswordFragment)

        }


    }

    private fun login() {
        Log.d(TAG, "login: ")
        binding.progressBar.visibility = VISIBLE
        val loginBody = LoginBody(
            password = binding.passwordEt.text.toString().trim(),
            phone = binding.phoneNoEt.text.toString().trim()
        )

        loginViewModel.login(loginBody)
    }

    private fun updateUi(response: ProfileResponse?) {
        Log.d(TAG, "updateUi: ")
        if (response != null) {
            with(Session) {
                profileResponse = response

                requireContext().startActivity(
                    Intent(requireContext(), NavigationDrawerActivity::class.java)
                )
                requireActivity().finish()
            }
        }
    }


    private fun checkAuth() {
        Log.d(TAG, "checkAuth: ")
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.access_token_preferences), Context.MODE_PRIVATE
        ) ?: return

        val token = sharedPref.getString(getString(R.string.access_token), null)
        Session.token = token.toString()


        if (token != null) {
            /** get profile to check if token is valid or invalid
             *  if its valid check if the user has verified OTP or not
             */
            loginViewModel.getProfile()
        }
        /**
         *   else means there is no token saved so we stay in login fragment
         *   for log in and check if the user has verified OTP or not
         */
    }

    /**
     * checks if the phone number and password are not empty
     */
    private fun checkInputs(): Boolean {
        return !(binding.passwordEt.text.toString().isEmpty()
                || binding.passwordEt.text.toString().isEmpty()
                )

    }

}