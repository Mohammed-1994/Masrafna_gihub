package com.example.masrafna.ui.navigation.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.AboutCondeitionsResponse
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.ui.navigation.profile.ProfileRepo
import io.reactivex.disposables.CompositeDisposable

class InfoViewModel : ViewModel() {


    private var infoRepo: InfoRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        infoRepo = InfoRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        infoRepo.networkStats
    }


    val aboutResponse: LiveData<AboutCondeitionsResponse?> by lazy {
        infoRepo.aboutResponse
    }

    fun getInfo() {
        infoRepo.getInfo()
    }


    val conditionsResponse: LiveData<AboutCondeitionsResponse?> by lazy {
        infoRepo.conditionsResponse
    }

    fun getConditions() {
        infoRepo.getConditions()
    }

}