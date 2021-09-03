package com.example.masrafna.ui.navigation.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.NewsDetails
import com.example.masrafna.data.models.NewsListModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val TAG = "NewsRepo myTag"

class NewsRepo(

    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {


    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    private val _newsResponse = MutableLiveData<NewsListModel?>()
    val newsListResponse: LiveData<NewsListModel?> get() = _newsResponse


    private val _newsDetailsResponse = MutableLiveData<NewsDetails?>()
    val newsDetailsResponse: LiveData<NewsDetails?> get() = _newsDetailsResponse


    fun getNews(page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getAllNews(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _newsResponse.postValue(it)
                        _networkState.postValue(NetworkStatus.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                    })
        )

    }

    fun getNewsDetails(id: String) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _newsDetailsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getNewsDetails: Error", it)
                    })
        )
    }


}