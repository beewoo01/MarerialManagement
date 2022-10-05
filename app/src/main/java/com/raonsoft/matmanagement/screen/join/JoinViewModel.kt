package com.raonsoft.matmanagement.screen.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Field

class JoinViewModel : BaseViewModel() {

    private val _signUpState = MutableLiveData<Int>()
    val signUpState: LiveData<Int> = _signUpState

    fun signUpAdmin(
        account_id: String,
        account_password: String,
        armyunit_name: String,
        armyunit_division: String,
        armyunit_regiment: String,
        armyunit_battalion: String
    ) = viewModelScope.launch(exceptionhandler) {

        val response = repository.signUpAccount(
            account_id = account_id,
            account_password = account_password,
            account_ispro = 1,
            armyunit_name = armyunit_name,
            armyunit_division = armyunit_division,
            armyunit_regiment = armyunit_regiment,
            armyunit_battalion = armyunit_battalion,
            accoundArmyUnit_permission = 1

        )

        if (response.isSuccessful) {

            val result = response.body()

            _signUpState.value = result ?: kotlin.run {
                4
            }

        }
    }
}