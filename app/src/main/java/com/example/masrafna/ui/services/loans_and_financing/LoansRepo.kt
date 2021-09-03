package com.example.masrafna.ui.services.loans_and_financing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.AvailableBankList
import com.example.masrafna.data.models.LoanDetailsByBank

import com.example.masrafna.data.models.LoanListResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val TAG = "LoansRepo myTag"

class LoansRepo(
    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState

    private val _loansListResponse = MutableLiveData<LoanListResponse?>()
    val loansListResponse: LiveData<LoanListResponse?> get() = _loansListResponse


    private val _availableBanksResponse = MutableLiveData<AvailableBankList?>()
    val availableBanksResponse: LiveData<AvailableBankList?> get() = _availableBanksResponse


    private val _loanDetailsByBankResponse = MutableLiveData<LoanDetailsByBank?>()
    val loanDetailsByBankResponse: LiveData<LoanDetailsByBank?> get() = _loanDetailsByBankResponse


    private val _bankLoansByTypeResponse = MutableLiveData<LoanListResponse?>()
    val bankLoansByTypeResponse: LiveData<LoanListResponse?> get() = _bankLoansByTypeResponse



    private val _fundingByTypeResponse = MutableLiveData<LoanListResponse?>()
    val fundingByTypeResponse: LiveData<LoanListResponse?> get() = _fundingByTypeResponse


    private val _loanDetailsResponse = MutableLiveData<LoanDetailsByBank?>()
    val loanDetailsResponse: LiveData<LoanDetailsByBank?> get() = _loanDetailsResponse


    fun getLoansByTypeAndBankType(loanType: Int, bankType: Int, page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getLoansByTypeAndBankType(loanType, bankType, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _loansListResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getLoansByTypeAndBankType: Error", it)
                    })
        )
    }

    fun getFundingByType(loanType: Int,page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getFundingBbyType(loanType, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _fundingByTypeResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getLoansByTypeAndBankType: Error", it)
                    })
        )
    }


    fun getAvailableBankList(loanId: String) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getAvailableBankList(loanId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _availableBanksResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getAvailableBankList: Error", it)
                    })
        )
    }

    fun getLoanDetailsByBank(
        loanId: String,
        bankId: String
    ) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getLoanDetailsByBankId(loanId, bankId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _loanDetailsByBankResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getLoanDetailsByBank: Error", it)
                    })
        )
    }


    fun getLoanDetails(
        loanId: String,

    ) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getLoanDetails(loanId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _loanDetailsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getLoanDetailsByBank: Error", it)
                    })
        )
    }


    fun getBankLoansByLoanType(loanType: Int, bankId: String, page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getBankLoansByLoanType(loanType, bankId, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankLoansByTypeResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankLoansByLoanType: Error", it)
                    })
        )
    }

    fun getFundingByTypeBankPage(loanType: Int, bankId: String, page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getFundingByTypeBankPage(loanType, bankId, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _bankLoansByTypeResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getBankLoansByLoanType: Error", it)
                    })
        )
    }

}