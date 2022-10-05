package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ItemOfProductsEntity(

    @SerializedName("item_idx")
    @Expose
    val item_idx: Int,

    @SerializedName("item_name")
    @Expose
    val item_name: String,

    @SerializedName("item_image")
    @Expose
    val item_image: String?,

    @SerializedName("account_account_idx")
    @Expose
    val account_account_idx: Int,

    @SerializedName("item_createtime")
    @Expose
    val item_createtime: String,

    @SerializedName("item_updatetime")
    @Expose
    val item_updatetime: String,

    @SerializedName("allCount")
    @Expose
    val allCount: Int,

    @SerializedName("remainingProduct")
    @Expose
    val remainingProduct: Int

) : Parcelable
