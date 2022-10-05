package com.raonsoft.matmanagement.screen.editProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class EditProductViewModel : BaseViewModel() {

    private val _updateProductState = MutableLiveData<Int>()
    val updateProductState: LiveData<Int> = _updateProductState

    fun updateProduct(
        accountIdx: Int,
        product_name: String,
        product_description: String,
        product_taginfo: String,
        product_status: Int,
        product_idx: Int

    ) = viewModelScope.launch(exceptionhandler) {

        val response = repository.updateProduct(
            product_account_idx = accountIdx,
            product_name= product_name,
            product_description= product_description,
            product_taginfo= product_taginfo,
            product_status= product_status,
            product_idx = product_idx
        )

        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                _updateProductState.value = it
            } ?: kotlin.run {
                _updateProductState.value = 0
            }
        }

        /*product_account_idx: Int,
        product_name: String,
        product_description: String,
        product_taginfo: String,
        product_status: Int,
        product_idx: Int*/
    }
}