package com.example.masrafna.ui.navigation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.ArticleDetails
import com.example.masrafna.data.models.ArticleListModel
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel : ViewModel() {


    private var homeRepo: HomeRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        homeRepo = HomeRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        homeRepo.networkStats
    }


    val articleListResponse: LiveData<ArticleListModel?> by lazy {
        homeRepo.articleListResponse
    }

    fun getAllArticle(page: Int) {
        homeRepo.getAllArticles(page)
    }

    val articleDetailsResponse: LiveData<ArticleDetails?> by lazy {
        homeRepo.articleDetailsResponse
    }

    fun getArticleDetails(id: String) {
        homeRepo.getArticleDetails(id)
    }


}