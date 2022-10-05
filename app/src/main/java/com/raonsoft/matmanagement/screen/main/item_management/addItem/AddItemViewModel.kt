package com.raonsoft.matmanagement.screen.main.item_management.addItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.ftp.FTPRepository
import com.raonsoft.matmanagement.util.ftp.Result
import kotlinx.coroutines.*

class AddItemViewModel : BaseViewModel() {

    private val _uploadImageToFtpState = MutableLiveData<Int>()
    val uploadImageToFtpState: LiveData<Int> = _uploadImageToFtpState

    private val _registerItem = MutableLiveData<Int>()
    val registerItem: LiveData<Int> = _registerItem

    fun registerItem(item_name: String, item_image: String?, account_idx: Int) =
        viewModelScope.launch(exceptionhandler) {

            Log.wtf("AddItemViewModel", "registerItem")
            val response = repository.registItem(
                item_name = item_name,
                item_image = item_image,
                account_idx = account_idx
            )

            if (response.isSuccessful) {
                val result = response.body()

                result?.let {
                    _registerItem.value = it
                }
            }

        }

    fun uploadImage(imagePath: String) {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            uploadImageToFTP(imagePath)
        }
    }

    private suspend fun uploadImageToFTP(imagePath: String) =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            when (val result: Result<Boolean> = FTPRepository().uploadImage(imagePath)) {

                is Result.Success -> {
                    if (result.data) {
                        //성공!!
                        Log.wtf("saveVideo", "이미지 성공")

                        _uploadImageToFtpState.postValue(0)


                    } else {
                        Log.wtf("saveVideo", "이미지 실패")
                        _uploadImageToFtpState.postValue(1)

                    }
                }

                is Result.Failed -> {
                    Log.wtf("saveVideo", "이미지 실패")
                    _uploadImageToFtpState.postValue(1)

                }

                is Result.Error -> {
                    Log.wtf("saveVideo", "이미지 실패" + result.exception)
                    _uploadImageToFtpState.postValue(2)

                }

            }
        }
}