package com.raonsoft.matmanagement.screen.main.setting.changeInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.ftp.FTPRepository
import com.raonsoft.matmanagement.util.ftp.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeInfoViewModel : BaseViewModel() {

    private val _changeInfo = MutableLiveData<Int>()
    val changeInfo: LiveData<Int> = _changeInfo

    private val _getAccountDetailInfoMuLiveData = MutableLiveData<HashMap<String, Any>>()

    val getAccountDetailInfoLiveData: LiveData<HashMap<String, Any>> =
        _getAccountDetailInfoMuLiveData

    private val _uploadImageToFtpState = MutableLiveData<Int>()
    val uploadImageToFtpState: LiveData<Int> = _uploadImageToFtpState


    fun changeInfo(
        account_idx: Int,
        account_password: String,
        account_name: String,
        account_profile: String?,
        armyunit_name: String,
        armyunit_division: String,
        armyunit_regiment: String,
        armyunit_battalion: String
    ) = viewModelScope.launch(exceptionhandler) {
        val response = repository.updateAccountInfo(
            account_idx = account_idx,
            account_password = account_password,
            account_name = account_name,
            account_profile = account_profile,
            armyunit_name = armyunit_name,
            armyunit_division = armyunit_division,
            armyunit_regiment = armyunit_regiment,
            armyunit_battalion = armyunit_battalion
        )

        if (response.isSuccessful) {
            val result = response.body()

            result?.let {
                _changeInfo.value = it
            }
        }
    }

    fun getAccountDetailInto(account_idx: Int) = viewModelScope.launch(exceptionhandler) {

        val response = repository.getAccountDetailInfo(account_idx = account_idx)

        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                _getAccountDetailInfoMuLiveData.value = it
            }
        } else {
            Log.wtf("getAccountDetailInto", "response.else")
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