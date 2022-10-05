package com.raonsoft.matmanagement.model


data class AccountStateModel(
    override val uid: Long,
    override val type: CellType = CellType.ACCOUNT_CELL,
    val accoundArmyUnit_idx: Int,
    val accoundArmyUnit_createtime: String,
    val account_idx: Int,
    val account_name: String
) : Model(uid, type)
