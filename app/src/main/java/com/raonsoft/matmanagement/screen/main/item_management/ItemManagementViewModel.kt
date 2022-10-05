package com.raonsoft.matmanagement.screen.main.item_management

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.model.ItemOfProductsModel
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class ItemManagementViewModel: BaseViewModel() {

    private val _productCountOfItems = MutableLiveData<List<ItemOfProductsModel>>()
    val productCountOfItems : LiveData<List<ItemOfProductsModel>> = _productCountOfItems

    fun getProductCountOfItems(account_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.getProductCountOfItems(account_idx = account_idx)
        if (response.isSuccessful) {
            val result = response.body()
            Log.wtf("HomeViewModel", "getProductCountOfItems")

            _productCountOfItems.value = result?.map { entity ->
                ItemOfProductsModel(
                    uid = entity.hashCode().toLong(),
                    type = CellType.ITEM_OF_PRODUCTS,
                    item_idx = entity.item_idx,
                    item_name = entity.item_name,
                    item_image = entity.item_image,
                    account_account_idx = entity.account_account_idx,
                    item_createtime = entity.item_createtime,
                    item_updatetime = entity.item_updatetime,
                    allCount = entity.allCount,
                    remainingProduct = entity.remainingProduct
                )
            }
        }
    }
}