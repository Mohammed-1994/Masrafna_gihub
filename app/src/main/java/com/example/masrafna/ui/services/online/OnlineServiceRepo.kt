package com.example.masrafna.ui.services.online

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankBranche
import com.example.masrafna.data.models.OnlineServiceDetails
import com.example.masrafna.data.models.OnlineServiceModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val TAG = "OnlineServiceRepo myTag"

class OnlineServiceRepo(
    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {


    private val _onlineServiceBanksList = MutableLiveData<OnlineServiceModel?>()
    val onlineServiceBanksList: LiveData<OnlineServiceModel?> get() = _onlineServiceBanksList


    private val _onlineServiceDetails = MutableLiveData<OnlineServiceDetails?>()
    val onlineServiceDetails: LiveData<OnlineServiceDetails?> get() = _onlineServiceDetails

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    fun getOnlineServiceBanks(page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getOnlineServiceBanks(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _onlineServiceBanksList.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getOnlineServiceBanks: Error", it)
                    })
        )
    }

    fun getOnlineServiceDetails(bankId: String){
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getOnlineServiceDetails(bankId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _onlineServiceDetails.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getOnlineServiceBanks: Error", it)
                    })
        )
    }
}