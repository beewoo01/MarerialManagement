package com.raonsoft.matmanagement.screen.findaccount.findPW

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class FindPwViewModel : BaseViewModel() {

    private val _findPWState = MutableLiveData<String>()
    val findPWState: LiveData<String> = _findPWState

    fun findPWState(
        account_name: String,
        accoundArmyUnit_armyunit_idx: Int,
        account_id: String
    ) = viewModelScope.launch(exceptionhandler) {
        val response = repository.findPassword(
            account_name = account_name,
            accoundArmyUnit_armyunit_idx = accoundArmyUnit_armyunit_idx,
            account_id = account_id
        )
        if (response.isSuccessful) {

            val result = response.body()

            result?.let {
                _findPWState.value = it
            }

        }
    }
}