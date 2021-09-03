package com.example.masrafna.ui.navigation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.api.Status
import com.example.masrafna.data.auth.response.SignupResponse
import com.example.masrafna.data.models.NotificationModel
import com.example.masrafna.data.profile.body.UpdatePassword
import com.example.masrafna.data.profile.body.UpdateProfileBody
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.data.profile.response.UpdatePassResponse
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import retrofit2.HttpException


private const val TAG = "ProfileRepo myTag"

class ProfileRepo(
    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {


    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState

    private val _updateProfileResponse = MutableLiveData<ProfileResponse?>()
    val updateProfileResponse: LiveData<ProfileResponse?> get() = _updateProfileResponse

    private val _profileResponse = MutableLiveData<ProfileResponse?>()
    val profileResponse: LiveData<ProfileResponse?> get() = _profileResponse


    private val _notificationResponse = MutableLiveData<NotificationModel?>()
    val notificationResponse: LiveData<NotificationModel?> get() = _notificationResponse


    private val _updatePassResponse = MutableLiveData<UpdatePassResponse?>()
    val updatePassResponse: LiveData<UpdatePassResponse?> get() = _updatePassResponse


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
                        Log.e(TAG, "getProfile: $it")
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


    fun updateProfile(body: RequestBody) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.updateProfileBody(body)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _updateProfileResponse.postValue(it)

                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "updateProfile: Error $it")
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 401) {
                                Log.e(TAG, "updateProfile: 401")
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_401, gson.message)
                                )
                            } else if (it.code() == 422) {
                                Log.e(TAG, "updateProfile: 422")
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

    fun updatePassword(updatePassword: UpdatePassword) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.updatePassword(updatePassword)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _updatePassResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "updateProfile: Error $it")
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 401) {
                                Log.e(TAG, "updateProfile: 401")
                                val gson = Gson().fromJson(
                                    body?.string(), SignupResponse::class.java
                                )
                                _networkState.postValue(
                                    NetworkStatus(Status.CODE_401, gson.message)
                                )
                            } else if (it.code() == 422) {
                                Log.e(TAG, "updateProfile: 422")
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

    fun getNotifications() {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getNotifications()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _notificationResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "updateProfile: Error $it")
                        if (it is HttpException) {
                            val body = it.response()!!.errorBody()
                            if (it.code() == 401) {
                                Log.e(TAG, "updateProfile: 401")
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