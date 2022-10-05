package com.raonsoft.matmanagement.model

data class ItemOfProductsModel(
    override val uid: Long,
    override val type: CellType = CellType.ITEM_OF_PRODUCTS,
    val item_idx: Int,
    val item_name: String,
    val item_image: String?,
    val account_account_idx: Int,
    val item_createtime: String,
    val item_updatetime: String,
    val allCount: Int,
    val remainingProduct: Int

) : Model(uid, type)