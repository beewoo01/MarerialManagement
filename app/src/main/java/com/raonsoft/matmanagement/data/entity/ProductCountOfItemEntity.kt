package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ProductCountOfItemEntity(
    @SerializedName("product_idx")
    val product_idx : Int,
    @SerializedName("product_name")
    val product_name :String,
    @SerializedName("product_status")
    val product_status : Int,
    @SerializedName("provisionInfo_platoon")
    val provisionInfo_platoon : String,
    @SerializedName("provisionInfo_user_name")
    val provisionInfo_user_name : String,
    @SerializedName("special")
    val special : String,
    @SerializedName("provisionInfo_createtime")
    val provisionInfo_createtime : String
) : Parcelable