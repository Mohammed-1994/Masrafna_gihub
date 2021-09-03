package com.example.masrafna.ui.navigation.banks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankBranche
import com.example.masrafna.data.models.BankDetails
import com.example.masrafna.data.models.BankListModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val TAG = "BanksRepo myTag"

class BanksRepo(
    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _bankListResponse = MutableLiveData<BankListModel?>()
    val bankListListResponse: LiveData<BankListModel?> get() = _bankListResponse


    private val _bankDetailsResponse = MutableLiveData<BankDetails?>()
    val bankDetailsResponse: LiveData<BankDetails?> get() = _bankDetailsResponse

    private val _bankBranchesResponse = MutableLiveData<BankBranche?>()
    val bankBranchesResponse: LiveData<BankBranche?> get() = _bankBranchesResponse

    private val _bankPOSsResponse = MutableLiveData<BankBranche?>()
    val bankPOSsResponse: LiveData<BankBranche?> get() = _bankPOSsResponse

    private val _bankATMsResponse = MutableLiveData<BankBranche?>()
    val bankATMsResponse: LiveData<BankBranche?> get() = _bankATMsResponse

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    fun getBankList() {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getAllBanks()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankListResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankList: Error", it)
                    })
        )
    }

    fun searchBanksByName(name: String) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.searchBanksByName(name)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "searchBanksByName: ${it.payload.data.size}")
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankListResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankList: Error", it)
                    })
        )
    }

    fun getBankDetails(id: String) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getBankDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankDetailsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankDetails: Error", it)
                    })
        )
    }

    fun getBankBranches(bankId: String, city: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getBranches(bankId, city)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankBranchesResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankBranches: Error", it)
                    })
        )
    }

    fun getBankPOSs(bankId: String, city: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getPOSs(bankId, city)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankPOSsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankBranches: Error", it)
                    })
        )
    }
    fun getBankATMs(bankId: String, city: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getATMs(bankId, city)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankATMsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankBranches: Error", it)
                    })
        )
    }
}

