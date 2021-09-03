package com.example.masrafna.ui.loging.reset_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.auth.request.ChagnePasswordBody
import com.example.masrafna.data.auth.request.ChangPassVerifyOTP
import com.example.masrafna.data.auth.request.ChangPassword
import com.example.masrafna.data.auth.response.ChangePasswordResponse
import com.example.masrafna.data.auth.response.ResetPasswordResponse
import com.example.masrafna.data.auth.response.ResetPasswordVerifyOTP
import io.reactivex.disposables.CompositeDisposable

class ResetPassViewModel : ViewModel() {
    private var resetPassRepo: ResetPassRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        resetPassRepo = ResetPassRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        resetPassRepo.networkStats
    }


    val resetPassResponse: LiveData<ResetPasswordResponse?> by lazy {
        resetPassRepo.resetPassResponse
    }

    fun resetPass(changPassword: ChangPassword) {
        resetPassRepo.resetPassword(changPassword)
    }

    val resetPassVerifyOTPResponse: LiveData<ResetPasswordVerifyOTP?> by lazy {
        resetPassRepo.verifyOTPResponse
    }

    fun verifyOtp(verifyTheCode: ChangPassVerifyOTP) {
        resetPassRepo.verifyTheCode(verifyTheCode)
    }


    val changePassResponse: LiveData<ChangePasswordResponse?> by lazy {
        resetPassRepo.changePassResponse
    }

    fun changePass(changePasswordBody: ChagnePasswordBody) {
        resetPassRepo.changePass(changePasswordBody)
    }



    val resendOtpResponse: LiveData<ResetPasswordVerifyOTP?> by lazy {
        resetPassRepo.resendOtpResponse
    }

    fun resendOtp(changPassword: ChangPassword) {
        resetPassRepo.resetPasswordResendVerifyOTP(changPassword)
    }


}