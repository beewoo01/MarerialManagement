package com.raonsoft.matmanagement.screen.productManagement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductManagementViewModel : BaseViewModel() {

    private val _scannedItemIdx = MutableLiveData<Int?>()
    val scannedItemIdx: LiveData<Int?> = _scannedItemIdx

    private val _disburseState = MutableLiveData<Int>()
    val disburseState: LiveData<Int> = _disburseState

    private val _productInfo = MutableLiveData<HashMap<String, Any?>>()
    val productInfo: LiveData<HashMap<String, Any?>> = _productInfo

    private val _productReturnState = MutableLiveData<Int>()
    val productReturnState: LiveData<Int> = _productReturnState


    /*fun getProductIdxOfTagInfo(product_tagInfo: String) = viewModelScope.launch(exceptionhandler) {

        val response = repository.getProductIdxOfTagInfo(product_taginfo = product_tagInfo)

        if (response.isSuccessful) {

            val result = response.body()

            result?.let {
                _scannedItemIdx.value = it
            } ?: kotlin.run {
                _scannedItemIdx.value = null
            }

        } else {
            _scannedItemIdx.value = null
        }

    }*/

    fun disburse(
        product_idx: Int,
        account_idx: Int,
        company: String,
        platoon: String,
        user_name: String,
        default: String
    ) = viewModelScope.launch(exceptionhandler) {

        val response = repository.provistionProduct(
            provisionInfo_product_idx = product_idx,
            provisionInfo_account_idx = account_idx,
            provisionInfo_company = company,
            provisionInfo_platoon = platoon,
            provisionInfo_user_name = user_name,
            provisionInfo_default = default
        )

        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                _disburseState.value = it
            }
        }
    }

    fun getProductInfo(productTagInfo: String) = viewModelScope.launch(exceptionhandler) {
        val response = repository.getProductInfoOfTag(productTagInfo)

        if (response.body() == null) {
            Log.wtf("getProductInfo", "response.body() == null")
        }

        if (response.isSuccessful) {
            val result = response.body()

            result?.let {
                _productInfo.value = it
            }
        }
    }


    fun productReturn(product_idx: Int) = viewModelScope.launch(exceptionhandler) {
        val response = repository.productReturn(product_idx)

        if (response.isSuccessful) {
            val result = response.body()

            result?.let {
                _productReturnState.value = it
            }
        }
    }
}