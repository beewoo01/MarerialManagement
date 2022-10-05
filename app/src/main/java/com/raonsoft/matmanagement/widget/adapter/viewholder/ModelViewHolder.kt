package com.raonsoft.matmanagement.widget.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.raonsoft.matmanagement.model.Model
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener

abstract class ModelViewHolder<M : Model>(
    binding : ViewBinding,
    protected val viewModel : BaseViewModel,
    protected val customResourcesProvider : CustomResourcesProvider
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun reset()

    open fun bindData(model: M) {
        reset()
    }

    abstract fun bindViews(model: M, adapterListener: AdapterListener?)
}