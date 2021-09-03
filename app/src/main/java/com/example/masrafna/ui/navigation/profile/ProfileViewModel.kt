package com.example.masrafna.ui.navigation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.NotificationModel
import com.example.masrafna.data.profile.body.UpdatePassword
import com.example.masrafna.data.profile.body.UpdateProfileBody
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.data.profile.response.UpdatePassResponse
import io.reactivex.disposables.CompositeDisposable
import okhttp3.RequestBody

class ProfileViewModel : ViewModel() {

    private var profileRepo: ProfileRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()


    init {
        profileRepo = ProfileRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        profileRepo.networkStats
    }


    val updateProfileResponse: LiveData<ProfileResponse?> by lazy {
        profileRepo.updateProfileResponse
    }

    fun updateProfile(body: RequestBody) {
        profileRepo.updateProfile(body)
    }


        val profileResponse: LiveData<ProfileResponse?> by lazy {
            profileRepo.profileResponse
        }

    fun getProfile() {
        profileRepo.getProfile()
    }

    val updatePassResponse: LiveData<UpdatePassResponse?> by lazy {
        profileRepo.updatePassResponse
    }

    fun updatePass(updatePassword: UpdatePassword) {
        profileRepo.updatePassword(updatePassword)
    }

    val notificationResponse: LiveData<NotificationModel?> by lazy {
        profileRepo.notificationResponse
    }

    fun getNotifications() {
        profileRepo.getNotifications()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}