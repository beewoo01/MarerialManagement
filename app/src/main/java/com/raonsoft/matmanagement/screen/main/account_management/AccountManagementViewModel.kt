package com.raonsoft.matmanagement.screen.main.account_management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class AccountManagementViewModel : BaseViewModel() {

    private val _accountList = MutableLiveData<List<AccountStateModel>>()
    val accountList: LiveData<List<AccountStateModel>> = _accountList

    fun getAccountApplyState(armyunit_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.getAccountList(armyunit_idx = armyunit_idx, permission = 1)

        if (response.isSuccessful) {

            val result = response.body()

            _accountList.value = result?.map { entity ->
                AccountStateModel(
                    uid = entity.hashCode().toLong(),
                    type = CellType.ACCOUNT_CELL,
                    accoundArmyUnit_idx = entity.accoundArmyUnit_idx,
                    accoundArmyUnit_createtime = entity.accoundArmyUnit_createtime,
                    account_idx = entity.account_idx,
                    account_name = entity.account_name
                )
            }
        }
    }
}