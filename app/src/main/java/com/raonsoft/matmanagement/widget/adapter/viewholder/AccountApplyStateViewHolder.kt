package com.raonsoft.matmanagement.widget.adapter.viewholder

import android.util.Log
import com.raonsoft.matmanagement.databinding.ViewholderAccountApplyStateBinding
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AccountStateListener
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener

class AccountApplyStateViewHolder(
    private val binding: ViewholderAccountApplyStateBinding,
    viewModel : BaseViewModel,
    customResourcesProvider: CustomResourcesProvider
) : ModelViewHolder<AccountStateModel>(binding, viewModel, customResourcesProvider){
    override fun reset() = Unit

    override fun bindData(model: AccountStateModel) {
        super.bindData(model)
        with(binding) {
            Log.wtf("AccountApplyStateViewHolder", "bindData")
            nameTxv.text = model.account_name
            dateTxv.text = model.accoundArmyUnit_createtime
        }
    }

    override fun bindViews(model: AccountStateModel, adapterListener: AdapterListener?) {
        with(binding) {
            Log.wtf("AccountApplyStateViewHolder", "bindViews")
            moreImb.setOnClickListener {
                if (adapterListener is AccountStateListener) {
                    adapterListener.onItemClick(model)
                }
            }
        }
    }


}