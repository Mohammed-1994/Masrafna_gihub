package com.example.masrafna.ui.services.online

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.ArticleListModel
import com.example.masrafna.data.models.OnlineServiceDetails
import com.example.masrafna.data.models.OnlineServiceModel
import com.example.masrafna.ui.navigation.home.HomeRepo
import io.reactivex.disposables.CompositeDisposable

class OnlineViewModel : ViewModel() {


    private var onlineServiceRepo: OnlineServiceRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        onlineServiceRepo = OnlineServiceRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        onlineServiceRepo.networkStats
    }


    val onlineServiceBanks: LiveData<OnlineServiceModel?> by lazy {
        onlineServiceRepo.onlineServiceBanksList
    }

    fun getOnlineServicesBanks(page: Int) {
        onlineServiceRepo.getOnlineServiceBanks(page)
    }

    val onlineServiceDetails: LiveData<OnlineServiceDetails?> by lazy {
        onlineServiceRepo.onlineServiceDetails
    }

    fun getOnlineServicesDetails(bankId: String) {
        onlineServiceRepo.getOnlineServiceDetails(bankId)
    }

}