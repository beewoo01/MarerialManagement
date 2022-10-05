package com.raonsoft.matmanagement.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DefaultEntity(
    override val uid: Long,
    val userIdx : Int,
    val userEmail : String,
    val userPassword : String,
    val userName : String

): Entity, Parcelable

/*Entity
* 1. 실제 서버에서 넘어온 테이블 매칭될 클래스
* 2. 웹에서는 실제 데이터베이스와 매칭괼 클래스 라는 정의
* */
