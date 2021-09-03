package com.example.masrafna.ui.loging.reset_pass

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.request.ChagnePasswordBody
import com.example.masrafna.data.auth.request.ChangPassVerifyOTP
import com.example.masrafna.data.auth.request.ChangPassword
import com.example.masrafna.data.auth.response.ChangePasswordResponse
import com.example.masrafna.data.auth.response.ResetPasswordVerifyOTP
import com.example.masrafna.databinding.FragmentResetPasswordBinding
import com.example.masrafna.util.Session
import com.google.android.material.snackbar.Snackbar
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

private const val TAG = "ResetPasswordFragment myTag"

class ResetPasswordFragment : Fragment() {

    private val resetPassViewModel: ResetPassViewModel by viewModels()
    private var phoneNO = ""
    private lateinit var binding: FragmentResetPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(resetPassViewModel) {
            networkStatus.observe(this@ResetPasswordFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE

                if (it.status == Status.CODE_404 || it.status == Status.CODE_422) {
                    Snackbar.make(
                        binding.root,
                        it.msg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            })

            resetPassResponse.observe(this@ResetPasswordFragment, {
                verifyTheCode()
            })

            resetPassVerifyOTPResponse.observe(this@ResetPasswordFragment, {
                verifiedSuccessful(it)
            })

            changePassResponse.observe(this@ResetPasswordFragment, {
                changeSuccess(it)
            })
            resendOtpResponse.observe(this@ResetPasswordFragment, {
                Snackbar.make(
                    binding.root,
                    "تم ارسال الرمز من جديد", Snackbar.LENGTH_LONG
                )
                    .show()
            })
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            confirmBtn.setOnClickListener {
                if (confirmBtn.text.toString().isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "تأكد من ادخال رقم الهاتف",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    phoneNO = phoneNoEt.text.toString().trim()
                    resetPassViewModel.resetPass(ChangPassword(phoneNO))
                }

            }

            sendAgainBtn.setOnClickListener {
                resetPassViewModel.resendOtp(ChangPassword(phoneNO))
            }
        }
    }

    // *** 2 **
    private fun verifyTheCode() {
        with(binding) {

            activity?.runOnUiThread {
                countre.visibility = VISIBLE
                phoneNoEt.text.clear()
                text.text = ""
                phoneNoEt.hint = "أدخل الرمز"
                sendAgainBtn.visibility = VISIBLE
            }
            confirmBtn.setOnClickListener {
                if (phoneNoEt.text.toString().isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "تأكد من ادخال الرمز المرسل",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else
                    resetPassViewModel.verifyOtp(
                        ChangPassVerifyOTP(
                            phoneNO,
                            phoneNoEt.text.toString().trim()
                        )
                    )
            }
        }


    }


    // *** 3 **
    private fun verifiedSuccessful(verifyOTPResponse: ResetPasswordVerifyOTP?) {

        if (verifyOTPResponse != null) {
            Session.token = verifyOTPResponse.payload.accessToken.token

            with(binding) {
                activity?.runOnUiThread {
                    countre.visibility = INVISIBLE
                    phoneNoEt.text.clear()
                    text.text = "أدخل كلمة السر الجديدة"
                    phoneNoEt.hint = "أدخل كلمة السر الجديدة"
                    sendAgainBtn.visibility = GONE
                    confirmBtn.visibility = VISIBLE
                    confirmBtn.setOnClickListener {
                        if (phoneNoEt.text.toString()
                                .isNotEmpty()
                        ) {
                            if (phoneNoEt.text.toString().length >= 8) {
                                changePassword(phoneNoEt.text.toString())
                            } else {
                                Snackbar.make(
                                    binding.root,
                                    "كلمة السر 8 أحرف على الأقل", Snackbar.LENGTH_LONG
                                )
                                    .show()
                            }

                        }
                    }
                }
            }
        }


    }


    // *** 4 **
    private fun changePassword(password: String) {
        binding.progressBar.visibility = VISIBLE
        val changePasswordBody = ChagnePasswordBody(password)
        resetPassViewModel.changePass(changePasswordBody)
    }

    // *** 5 **
    private fun changeSuccess(response: ChangePasswordResponse?) {
        if (response != null) {
            activity?.runOnUiThread {
                findNavController().navigate(R.id.action_nav_resetPasswordFragment_to_nav_login)

            }
        }
    }
}