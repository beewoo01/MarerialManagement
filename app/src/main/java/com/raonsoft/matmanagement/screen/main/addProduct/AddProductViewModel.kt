package com.raonsoft.matmanagement.screen.main.addProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class AddProductViewModel : BaseViewModel() {

    private val _registerProductState = MutableLiveData<Int>()
    val registerProduct: LiveData<Int> = _registerProductState

    fun registerProduct(
        product_name: String,
        product_description: String,
        product_item_idx: Int,
        product_account_idx: Int,
        product_taginfo: String
    ) = viewModelScope.launch {

        val response = repository.registProduct(
            product_name = product_name,
            product_description = product_description,
            product_item_idx = product_item_idx,
            product_account_idx = product_account_idx,
            product_taginfo = product_taginfo
        )

        if (response.isSuccessful) {
            val result = response.body()

            result?.let {
                _registerProductState.value = it
            }

        }

    }

}