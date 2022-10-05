package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrentProductContentEntity(
    @SerializedName("product_idx")
    val product_idx : Int,

    @SerializedName("product_name")
    val product_name : String,

    @SerializedName("product_status")
    val product_status : Int,

    @SerializedName("product_taginfo")
    val product_taginfo: String,

    @SerializedName("provisionInfo_company")
    val provisionInfo_company : String?,

    @SerializedName("provisionInfo_platoon")
    val provisionInfo_platoon : String?,

    @SerializedName("provisionInfo_user_name")
    val provisionInfo_user_name : String?,

    @SerializedName("special")
    val special : String,

    @SerializedName("provisionInfo_createtime")
    val provisionInfo_createtime : String?,


) : Parcelable
