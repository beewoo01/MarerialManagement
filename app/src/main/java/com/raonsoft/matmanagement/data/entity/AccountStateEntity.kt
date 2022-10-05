package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class AccountStateEntity(
    @SerializedName("accoundArmyUnit_idx")
    val accoundArmyUnit_idx: Int,
    @SerializedName("accoundArmyUnit_createtime")
    val accoundArmyUnit_createtime: String,
    @SerializedName("account_idx")
    val account_idx: Int,
    @SerializedName("account_name")
    val account_name: String
) : Parcelable