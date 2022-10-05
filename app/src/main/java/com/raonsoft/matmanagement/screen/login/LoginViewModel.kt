package com.raonsoft.matmanagement.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val _accountState = MutableLiveData<Int?>()
    val accountState: LiveData<Int?> = _accountState


    fun loginAccount(account_id: String, account_password: String, account_isPro: Int) =
        viewModelScope.launch(exceptionhandler) {
            val response = repository.loginAccount(account_id, account_password, account_isPro)

            if (response.isSuccessful) {

                val result = response.body()

                _accountState.value = result

            } else {

                _accountState.value = null
            }
        }

}