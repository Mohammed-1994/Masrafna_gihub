package com.example.masrafna.ui.loging.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.data.auth.request.VerifyOTPBody
import com.example.masrafna.data.auth.response.VerifyOTPResponse
import com.example.masrafna.databinding.FragmentConfirmCodeBinding
import com.example.masrafna.util.Session
import com.google.android.material.snackbar.Snackbar
import io.reactivex.schedulers.Schedulers
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.ui.loging.LoggingActivity
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import retrofit2.HttpException


private const val TAG = "ConfirmCodeFragment myTag"

class ConfirmCodeFragment : Fragment() {

    private val signupViewModel: SignupViewModel by viewModels()

    private lateinit var binding: FragmentConfirmCodeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signupViewModel.resendOTPResponse.observe(this,
            {
                if (it != null && !it.error) {
                    Snackbar.make(
                        binding.root,
                        "تم ارسال الكود من جديد", Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
        )
        signupViewModel.networkStatus.observe(this,
            {

                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE

                if (it.status == Status.CODE_422)
                    Snackbar.make(
                        binding.root,
                        it.msg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()


            })

        signupViewModel.verifyOTPResponse.observe(this,
            {
                updateUi(it)
            })

        signupViewModel.logoutResponse.observe(this, {
            logout()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            createBtn.setOnClickListener {
                if (!codeEt.text.toString().trim().isEmpty())
                    checkTheCode(codeEt.text.toString().trim())
                else
                    Snackbar.make(
                        binding.root,
                        "تأكد من ادخال الرمز",
                        Snackbar.LENGTH_LONG
                    ).show()
            }

            sendAgainBtn.setOnClickListener {
                signupViewModel.resendOTP()

            }

            logOut.setOnClickListener {
                signupViewModel.logout()
            }
        }
    }


    /**
     * checks the code if its correct comparing with the server side code.
     */
    private fun checkTheCode(code: String) {
        val verifyOtbBody = VerifyOTPBody(code)
        signupViewModel.verifyOTP(verifyOtbBody)

    }

    private fun updateUi(response: VerifyOTPResponse?) {
        if (response != null) {
            Session.verifyOTPResponse = response
            findNavController().navigate(R.id.action_nav_confirmCodeFragment_to_nav_welcomeFragment)
        }
    }

    private fun logout() {

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.access_token_preferences), Context.MODE_PRIVATE
        ) ?: return

        val token = sharedPref.getString(getString(R.string.access_token), null)


        with(sharedPref.edit()) {
            if (token != null)
                remove(getString(R.string.access_token))
            apply()
        }

        requireContext().startActivity(
            Intent(requireContext(), LoggingActivity::class.java)
        )
        activity?.runOnUiThread {
            binding.progressBar.visibility = GONE
        }
        requireActivity().finish()
    }


}