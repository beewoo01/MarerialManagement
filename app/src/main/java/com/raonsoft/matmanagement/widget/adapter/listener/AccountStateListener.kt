package com.raonsoft.matmanagement.widget.adapter.listener

import com.raonsoft.matmanagement.model.AccountStateModel

interface AccountStateListener: AdapterListener {
    fun onItemClick(model: AccountStateModel)
}