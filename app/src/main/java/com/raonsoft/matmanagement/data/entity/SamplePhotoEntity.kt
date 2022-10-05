package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class SamplePhotoEntity(

    @Expose(serialize = false, deserialize = false)
    override val uid: Long,

    @SerializedName("albumId")
    @Expose
    val albumId: Int,

    @SerializedName("id")
    @Expose
    val photoId: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("thumbnailUrl")
    @Expose
    val thumbnailUrl: String
): Entity, Parcelable