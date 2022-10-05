package com.raonsoft.matmanagement.widget.adapter.viewholder

import com.raonsoft.matmanagement.databinding.ViewholderEmptyBinding
import com.raonsoft.matmanagement.model.Model
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener

class EmptyViewHolder(
    private val binding: ViewholderEmptyBinding,
    viewModel : BaseViewModel,
    customResourcesProvider : CustomResourcesProvider
) : ModelViewHolder<Model>(binding, viewModel, customResourcesProvider){

    override fun reset() = Unit

    override fun bindViews(model: Model, adapterListener: AdapterListener?) = Unit

}