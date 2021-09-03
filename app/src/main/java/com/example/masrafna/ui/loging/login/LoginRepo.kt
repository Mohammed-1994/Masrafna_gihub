package com.example.masrafna.ui.loging.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.request.LoginBody
import com.example.masrafna.data.auth.response.LoginResponse
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.data.auth.response.SignupResponse
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


private const val TAG = "LoginRepo myTag"

class LoginRepo(

    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    private val _profileResponse = MutableLiveData<ProfileResponse?>()
    val profileResponse: LiveData<ProfileResponse?> get() = _profileResponse

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState

    fun login(loginBody: LoginBody) {
        Log.d(TAG, "login: ")
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.login(loginBody)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _loginResponse.postValue(it)
                        _networkState.postValue(NetworkStatus.LOADED)

                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "login: Error", it)
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 422) {
                                Log.e(TAG, "login: 422")
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

    fun getProfile() {
        Log.d(TAG, "getProfile:")

        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            MainAPIClient.getClient().getProfile()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "getProfile: ${it.message}")
                        _networkState.postValue(NetworkStatus.LOADED)
                        _profileResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getProfile: $it", )
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 401) {
                                Log.e(TAG, "getProfile: 401")
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_401, gson.message)
                                )
                            }
                        }
                    })
        )
    }

}