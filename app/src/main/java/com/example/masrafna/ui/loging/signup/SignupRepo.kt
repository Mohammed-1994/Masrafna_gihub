package com.example.masrafna.ui.loging.signup

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.request.SignUpBody
import com.example.masrafna.data.auth.request.VerifyOTPBody
import com.example.masrafna.data.auth.response.LogoutResponse
import com.example.masrafna.data.auth.response.SignupResponse
import com.example.masrafna.data.auth.response.VerifyOTPResponse
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

private const val TAG = "SignupRepo myTag"

class SignupRepo(

    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _signUpResponse = MutableLiveData<SignupResponse?>()
    val signupResponse: LiveData<SignupResponse?> get() = _signUpResponse


    private val _verifyOTPResponse = MutableLiveData<VerifyOTPResponse?>()
    val verifyOTPResponse: LiveData<VerifyOTPResponse?> get() = _verifyOTPResponse


    private val _resendOTPResponse = MutableLiveData<VerifyOTPResponse?>()
    val resendOTPResponse: LiveData<VerifyOTPResponse?> get() = _resendOTPResponse


    private val _logoutResponse = MutableLiveData<LogoutResponse?>()
    val logoutResponse: LiveData<LogoutResponse?> get() = _logoutResponse


    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    fun signUp(signUpBody: SignUpBody) {
        _networkState.postValue(NetworkStatus.LOADING)

        compositeDisposable.add(
            apiService.signup(signUpBody)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _signUpResponse.postValue(it)
                        _networkState.postValue(NetworkStatus.LOADED)

                    },
                    {
                        Log.e(TAG, "login: Error", it)

                        _networkState.postValue(NetworkStatus.ERROR)
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

                    }
                )
        )
    }

    fun verifyOTP(verifyOTPBody: VerifyOTPBody) {
        Log.d(TAG, "verifyOTP: ")
        _networkState.postValue(NetworkStatus.LOADING)

        compositeDisposable.add(
            apiService.verifyOTP(verifyOTPBody)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "verifyOTP: ${it.message}")
                        _verifyOTPResponse.postValue(it)
                        _networkState.postValue(NetworkStatus.LOADED)

                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
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

                    }
                )
        )
    }


    fun resendOTP() {
        Log.d(TAG, "verifyOTP: ")
        _networkState.postValue(NetworkStatus.LOADING)

        compositeDisposable.add(
            apiService.resendOTPCode()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "verifyOTP: ${it.message}")
                        _resendOTPResponse.postValue(it)
                        _networkState.postValue(NetworkStatus.LOADED)

                    },
                    {
                        Log.e(TAG, "verifyOTP: $it")
                        _networkState.postValue(NetworkStatus.ERROR)
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

                    }
                )
        )

    }

    fun logout() {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.logout()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _logoutResponse.postValue(it)

                    },
                    {
                        _logoutResponse.postValue(null)
                    })
        )
    }
}