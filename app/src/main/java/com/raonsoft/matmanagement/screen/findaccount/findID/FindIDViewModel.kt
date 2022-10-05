package com.raonsoft.matmanagement.screen.findaccount.findID

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class FindIDViewModel: BaseViewModel() {

    private val _findIDState = MutableLiveData<String>()
    val findIDState :LiveData<String> = _findIDState

    fun findIDState(account_name: String, accoundArmyUnit_armyunit_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.findID(account_name, accoundArmyUnit_armyunit_idx)
        if (response.isSuccessful) {

            val result = response.body()
            Log.wtf("FindIDViewModel", "result is Successful")
            result?.let {
                Log.wtf("FindIDViewModel", "result is $it")
                _findIDState.value = it
            }

        }
    }
}