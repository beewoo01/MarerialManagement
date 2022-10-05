package com.raonsoft.matmanagement.widget.adapter.listener

import com.raonsoft.matmanagement.model.ItemOfProductsModel

interface ItemOfProductsListener : AdapterListener {
    fun onItemClick(model: ItemOfProductsModel)
    fun onItemStateClick(model: ItemOfProductsModel)
}