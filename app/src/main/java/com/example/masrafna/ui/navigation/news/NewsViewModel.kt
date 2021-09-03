package com.example.masrafna.ui.navigation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.NewsDetails
import com.example.masrafna.data.models.NewsListModel
import io.reactivex.disposables.CompositeDisposable


private const val TAG = "NewsViewModel myTag"

class NewsViewModel : ViewModel() {


    private var newsRepo: NewsRepo
    private val compositeDisposable = CompositeDisposable()
    private val mainInterface = MainAPIClient.getClient()

    init {
        newsRepo = NewsRepo(mainInterface, compositeDisposable)
    }


    val networkStatus: LiveData<NetworkStatus> by lazy {
        newsRepo.networkStats
    }

    val newsListResponse: LiveData<NewsListModel?> by lazy {
        newsRepo.newsListResponse
    }

    fun getNews(page: Int) {
        newsRepo.getNews(page)
    }


    val newsDetailsResponse: LiveData<NewsDetails?> by lazy {
        newsRepo.newsDetailsResponse
    }

    fun getNewsDetails(id: String) {
        newsRepo.getNewsDetails(id)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}