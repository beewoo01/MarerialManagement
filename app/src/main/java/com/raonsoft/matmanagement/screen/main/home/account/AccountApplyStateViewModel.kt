package com.raonsoft.matmanagement.screen.main.home.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class AccountApplyStateViewModel: BaseViewModel() {

    private val _accountApplyList = MutableLiveData<List<AccountStateModel>>()
    val accountApplyList: LiveData<List<AccountStateModel>> = _accountApplyList

    fun getAccountApplyState(armyunit_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.getAccountList(armyunit_idx = armyunit_idx, permission = 0)

        if (response.isSuccessful) {
            val result = response.body()
            _accountApplyList.value = result?.map {

                AccountStateModel(
                    uid = it.hashCode().toLong(),
                    type = CellType.ACCOUNT_CELL,
                    accoundArmyUnit_idx = it.accoundArmyUnit_idx,
                    accoundArmyUnit_createtime = it.accoundArmyUnit_createtime,
                    account_idx = it.account_idx,
                    account_name = it.account_name

                )
            }

        }
    }


}