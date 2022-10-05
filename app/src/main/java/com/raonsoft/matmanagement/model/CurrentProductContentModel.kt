package com.raonsoft.matmanagement.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentProductContentModel(
    override val uid: Long,
    override val type: CellType = CellType.CURRENT_PRODUCT_CONTENT,

    val product_idx: Int,
    val product_name: String,
    val product_status: Int,
    val product_taginfo: String,
    val provisionInfo_company: String?,
    val provisionInfo_platoon: String?,
    val provisionInfo_user_name: String?,
    val special: String,
    val provisionInfo_createtime: String?

) : Model(uid, type), Parcelable
