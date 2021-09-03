package com.example.masrafna.ui.navigation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.masrafna.api.MainInterface
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.ArticleDetails
import com.example.masrafna.data.models.ArticleListModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val TAG = "HomeRepo myTag"

class HomeRepo(

    private val apiService: MainInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkStatus>()
    val networkStats: LiveData<NetworkStatus>
        get() = _networkState


    private val _articleResponse = MutableLiveData<ArticleListModel?>()
    val articleListResponse: LiveData<ArticleListModel?> get() = _articleResponse


    private val _articleDetailsResponse = MutableLiveData<ArticleDetails?>()
    val articleDetailsResponse: LiveData<ArticleDetails?> get() = _articleDetailsResponse


    fun getAllArticles(page: Int) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getAllArticles(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _articleResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getAllArticles: Error", it)
                    })
        )
    }

    fun getArticleDetails(id: String) {
        _networkState.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiService.getArticleDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _networkState.postValue(NetworkStatus.LOADED)
                        _articleDetailsResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkStatus.ERROR)
                        Log.e(TAG, "getArticleDetails: Error", it)
                    })
        )
    }

}