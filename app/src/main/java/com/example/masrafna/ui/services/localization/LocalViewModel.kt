package com.example.masrafna.ui.services.localization

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankLocalizationModel
import com.example.masrafna.data.models.Localizations
import io.reactivex.disposables.CompositeDisposable

class LocalViewModel : ViewModel() {


    private var localRepo: LocalRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        localRepo = LocalRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        localRepo.networkStats
    }


    val localizationsResponse: LiveData<Localizations?> by lazy {
        localRepo.localizationResponse
    }

    fun getLocalizations(page: Int) {
        localRepo.getLocalizations(page)
    }

    val bankLocalResponse: LiveData<BankLocalizationModel?> by lazy {
        localRepo.localBankResponse
    }

    fun getBankLocalizations(id: String) {
        localRepo.getBankLocal(id)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}