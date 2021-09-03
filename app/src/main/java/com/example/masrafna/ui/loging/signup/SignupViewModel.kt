package com.example.masrafna.ui.loging.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.auth.request.SignUpBody
import com.example.masrafna.data.auth.request.VerifyOTPBody
import com.example.masrafna.data.auth.response.LogoutResponse
import com.example.masrafna.data.auth.response.SignupResponse
import com.example.masrafna.data.auth.response.VerifyOTPResponse
import io.reactivex.disposables.CompositeDisposable

private const val TAG = "SignupViewModel myTag"

class SignupViewModel : ViewModel() {

    private var signupRepo: SignupRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        signupRepo = SignupRepo(mainInterface, compositeDisposable)
    }

    val signUpResponse: LiveData<SignupResponse?> by lazy {
        signupRepo.signupResponse
    }

    val verifyOTPResponse: LiveData<VerifyOTPResponse?> by lazy {
        signupRepo.verifyOTPResponse
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        signupRepo.networkStats
    }

    val resendOTPResponse: LiveData<VerifyOTPResponse?> by lazy {
        signupRepo.resendOTPResponse
    }
    val logoutResponse: LiveData<LogoutResponse?> by lazy {
        signupRepo.logoutResponse
    }

    fun signup(signUpBody: SignUpBody) {
        signupRepo.signUp(signUpBody)
    }


    fun verifyOTP(verifyOTPBody: VerifyOTPBody) {
        Log.d(TAG, "verifyOTP: ")
        signupRepo.verifyOTP(verifyOTPBody)
    }

    fun resendOTP() {
        Log.d(TAG, "resendOTP: ")
        signupRepo.resendOTP()
    }
    fun logout() {
        Log.d(TAG, "resendOTP: ")
        signupRepo.logout()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}