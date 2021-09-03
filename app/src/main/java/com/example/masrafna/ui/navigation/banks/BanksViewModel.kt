package com.example.masrafna.ui.navigation.banks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankBranche
import com.example.masrafna.data.models.BankDetails
import com.example.masrafna.data.models.BankListModel
import io.reactivex.disposables.CompositeDisposable

class BanksViewModel : ViewModel() {
    private var banksRepo: BanksRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        banksRepo = BanksRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        banksRepo.networkStats
    }


    val banksListResponse: LiveData<BankListModel?> by lazy {
        banksRepo.bankListListResponse
    }

    fun getBanksList() {
        banksRepo.getBankList()
    }

    val banksDetailsResponse: LiveData<BankDetails?> by lazy {
        banksRepo.bankDetailsResponse
    }

    fun getBanksDetails(id: String) {
        banksRepo.getBankDetails(id)
    }

    val banksBranchesResponse: LiveData<BankBranche?> by lazy {
        banksRepo.bankBranchesResponse
    }

    fun getBanksBranches(bankId: String, city: Int) {
        banksRepo.getBankBranches(bankId, city)
    }

    val banksPOSsResponse: LiveData<BankBranche?> by lazy {
        banksRepo.bankPOSsResponse
    }

    fun getBanksPOSs(bankId: String, city: Int) {
        banksRepo.getBankPOSs(bankId, city)
    }


    val banksAtMsResponse: LiveData<BankBranche?> by lazy {
        banksRepo.bankATMsResponse
    }

    fun getBanksATMs(bankId: String, city: Int) {
        banksRepo.getBankATMs(bankId, city)
    }

    fun searchBanksByName(filter: String) {
        banksRepo.searchBanksByName(filter)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}