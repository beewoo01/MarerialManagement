package com.raonsoft.matmanagement.screen.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.model.CurrentProductContentModel
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductViewModel : BaseViewModel() {

    private val _productOfItemList = MutableLiveData<List<CurrentProductContentModel>>()
    val productOfItemList: LiveData<List<CurrentProductContentModel>> = _productOfItemList

    private val _deleteProductState = MutableLiveData<Int>()
    val deleteProductState: LiveData<Int> = _deleteProductState

    fun getProductOfItemIdx(item_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.getProductOfItemIdx(item_idx = item_idx)

        if (response.isSuccessful) {
            val result = response.body()
            _productOfItemList.value = result?.map { entity ->
                CurrentProductContentModel(
                    uid = entity.hashCode().toLong(),
                    type = CellType.CURRENT_PRODUCT_CONTENT,
                    product_idx = entity.product_idx,
                    product_name = entity.product_name,
                    product_taginfo = entity.product_taginfo,
                    product_status = entity.product_status,
                    provisionInfo_company = entity.provisionInfo_company,
                    provisionInfo_platoon = entity.provisionInfo_platoon,
                    provisionInfo_user_name = entity.provisionInfo_user_name,
                    special = entity.special,
                    provisionInfo_createtime = entity.provisionInfo_createtime
                )
            }
        }
    }

    fun deleteProduct(item_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.deleteProduct(item_idx = item_idx)

        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                _deleteProductState.value = it
            } ?: kotlin.run {
                _deleteProductState.value = 0
            }
        }
    }

}