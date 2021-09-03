package com.example.masrafna.ui.navigation.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.AboutCondeitionsResponse
import com.example.masrafna.data.profile.response.ProfileResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InfoRepo(
    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    private val _aboutResponse = MutableLiveData<AboutCondeitionsResponse?>()
    val aboutResponse: LiveData<AboutCondeitionsResponse?> get() = _aboutResponse


    private val _conditionsResponse = MutableLiveData<AboutCondeitionsResponse?>()
    val conditionsResponse: LiveData<AboutCondeitionsResponse?> get() = _conditionsResponse


    fun getConditions() {

        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getConditions()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _conditionsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                    })
        )
    }

    fun getInfo() {

        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getInfo()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _aboutResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                    })
        )
    }

}