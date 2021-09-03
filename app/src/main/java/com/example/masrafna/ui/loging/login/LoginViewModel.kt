package com.example.masrafna.ui.loging.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.auth.request.LoginBody
import com.example.masrafna.data.auth.response.LoginResponse
import com.example.masrafna.data.profile.response.ProfileResponse
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel : ViewModel() {
    private var loginRepo: LoginRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()


    init {
        loginRepo = LoginRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        loginRepo.networkStats
    }


    val loginResponse: LiveData<LoginResponse?> by lazy {
        loginRepo.loginResponse
    }

    fun login(loginBody: LoginBody) {
        loginRepo.login(loginBody)
    }

    val profileResponse: LiveData<ProfileResponse?> by lazy {
        loginRepo.profileResponse
    }

    fun getProfile() {
        loginRepo.getProfile()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }

}