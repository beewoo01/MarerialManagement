package com.raonsoft.matmanagement.widget.adapter.listener

import com.raonsoft.matmanagement.model.CurrentProductContentModel

interface ProductOfItemListener : AdapterListener {
    fun onItemClick(model : CurrentProductContentModel)
}