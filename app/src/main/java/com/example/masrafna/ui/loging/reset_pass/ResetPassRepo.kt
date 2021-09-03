package com.example.masrafna.ui.loging.reset_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.request.ChagnePasswordBody
import com.example.masrafna.data.auth.request.ChangPassVerifyOTP
import com.example.masrafna.data.auth.request.ChangPassword
import com.example.masrafna.data.auth.response.ChangePasswordResponse
import com.example.masrafna.data.auth.response.ResetPasswordResponse
import com.example.masrafna.data.auth.response.ResetPasswordVerifyOTP
import com.example.masrafna.data.auth.response.SignupResponse
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class ResetPassRepo(

    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState

    private val _resetPassResponse = MutableLiveData<ResetPasswordResponse?>()
    val resetPassResponse: LiveData<ResetPasswordResponse?>
        get() = _resetPassResponse


    private val _verifyOTPResponse = MutableLiveData<ResetPasswordVerifyOTP?>()
    val verifyOTPResponse: LiveData<ResetPasswordVerifyOTP?>
        get() = _verifyOTPResponse


    private val _changePassResponse = MutableLiveData<ChangePasswordResponse?>()
    val changePassResponse: LiveData<ChangePasswordResponse?>
        get() = _changePassResponse


    private val _resendOtpResponse = MutableLiveData<ResetPasswordVerifyOTP?>()
    val resendOtpResponse: LiveData<ResetPasswordVerifyOTP?>
        get() = _resendOtpResponse


    fun resetPassword(changPassword: ChangPassword) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.resetPassword(changPassword)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _resetPassResponse.postValue(it)
                    },
                    {
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 404) {

                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_404, "الرقم المدخل غير مسجل")
                                )
                            }
                        }

                    })
        )
    }

    fun verifyTheCode(verifyTheCode: ChangPassVerifyOTP) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.resetPasswordVerifyOTP(verifyTheCode)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _verifyOTPResponse.postValue(it)
                    },
                    {
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 422) {
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_422, gson.message)
                                )
                            }
                        }

                    })
        )
    }

    fun changePass(changePasswordBody: ChagnePasswordBody) {

        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.changePassword(changePasswordBody)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _changePassResponse.postValue(it)
                    },
                    {
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 422) {
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_422, gson.message)
                                )
                            }
                        }

                    })
        )
    }

    fun resetPasswordResendVerifyOTP(changPassword: ChangPassword){

        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.resetPasswordResendVerifyOTP(changPassword)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _resendOtpResponse.postValue(it)
                    },
                    {
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 422) {
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_422, gson.message)
                                )
                            }
                        }

                    })
        )
    }

}