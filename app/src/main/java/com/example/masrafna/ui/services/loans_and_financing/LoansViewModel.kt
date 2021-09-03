package com.example.masrafna.ui.services.loans_and_financing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.AvailableBankList
import com.example.masrafna.data.models.BankLoansByLoanType
import com.example.masrafna.data.models.LoanDetailsByBank
import com.example.masrafna.data.models.LoanListResponse
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.ui.navigation.profile.ProfileRepo
import io.reactivex.disposables.CompositeDisposable
import okhttp3.RequestBody

class LoansViewModel : ViewModel() {

    private var loansRepos: LoansRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()


    init {
        loansRepos = LoansRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        loansRepos.networkStats
    }


    val loansListResponse: LiveData<LoanListResponse?> by lazy {
        loansRepos.loansListResponse
    }

    fun getLoansByTypeAndBankType(loanType: Int, bankType: Int, page: Int) {
        loansRepos.getLoansByTypeAndBankType(loanType, bankType, page)
    }


    val fundingByTypeResponse: LiveData<LoanListResponse?> by lazy {
        loansRepos.fundingByTypeResponse
    }

    fun getFundingByType(loanType: Int, page: Int) {
        loansRepos.getFundingByType(loanType, page)
    }


    val availableBankResponse: LiveData<AvailableBankList?> by lazy {
        loansRepos.availableBanksResponse
    }

    fun getAvailableBankList(loanId: String) {
        loansRepos.getAvailableBankList(loanId)
    }


    val loanDetailsByBankResponse: LiveData<LoanDetailsByBank?> by lazy {
        loansRepos.loanDetailsByBankResponse
    }

    fun getLoanDetailsByBank(
        loanId: String,
        bankId: String
    ) {
        loansRepos.getLoanDetailsByBank(loanId, bankId)
    }

    val loanDetails: LiveData<LoanDetailsByBank?> by lazy {
        loansRepos.loanDetailsResponse
    }

    fun getLoanDetails(
        loanId: String
    ) {
        loansRepos.getLoanDetails(loanId)
    }

    val bankLoansByTypeResponse: LiveData<LoanListResponse?> by lazy {
        loansRepos.bankLoansByTypeResponse
    }

    fun getBankLoansByType(
        loanType: Int,
        bankId: String,
        page: Int
    ) {
        loansRepos.getBankLoansByLoanType(loanType, bankId, page)
    }
    fun getFundingByTypeBankPage(
        loanType: Int,
        bankId: String,
        page: Int
    ) {
        loansRepos.getBankLoansByLoanType(loanType, bankId, page)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}