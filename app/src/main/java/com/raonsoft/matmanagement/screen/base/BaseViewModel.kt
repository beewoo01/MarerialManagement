package com.raonsoft.matmanagement.screen.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebros.eripple.data.network.FetchState
import com.raonsoft.matmanagement.data.network.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel: ViewModel() {

    protected var stateBundle : Bundle? = null

    val repository: Repository = Repository()

    protected val _fetchState = MutableLiveData<FetchState>()
    val fetchState: LiveData<FetchState>
        get() = _fetchState

    protected val exceptionhandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()

        when (throwable) {
            is SocketException -> _fetchState.postValue(FetchState.BAD_INTERNET)
            is HttpException -> _fetchState.postValue(FetchState.PARSE_ERROR)
            is UnknownHostException -> _fetchState.postValue(FetchState.WRONG_CONNECTION)
            else -> _fetchState.postValue(FetchState.FAIL)
        }
    }

    open fun fetchData(): Job = viewModelScope.launch { }

    open fun storeState(stateBundle: Bundle) {
        this.stateBundle = stateBundle
    }

}