package com.example.masrafna.api

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    NO_MORE_NEWS,
    WRONG_CREDENTIALS,
    ATTEMPTS_EXCEEDED,
    INVALID_OTP,
    CODE_422,
    CODE_404,
    CODE_401,

}

class NetworkStatus(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkStatus = NetworkStatus(Status.SUCCESS, "Success")
        val END_OF_LIST: NetworkStatus = NetworkStatus(Status.NO_MORE_NEWS, "No more news")
        val LOADING: NetworkStatus = NetworkStatus(Status.RUNNING, "Running")
        val ERROR: NetworkStatus = NetworkStatus(Status.FAILED, "Failed")
    }
}