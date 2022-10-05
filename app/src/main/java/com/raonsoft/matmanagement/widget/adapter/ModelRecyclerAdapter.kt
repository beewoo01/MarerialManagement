package com.raonsoft.matmanagement.widget.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.model.Model
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.mapper.ModelViewHolderMapper
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener
import com.raonsoft.matmanagement.widget.adapter.viewholder.ModelViewHolder

class ModelRecyclerAdapter<M : Model, VM : BaseViewModel>(
    private var modelList: List<Model>,
    private var viewModel: VM,
    private val customResourcesProvider: CustomResourcesProvider,
    private val adapterListener: AdapterListener?

) : ListAdapter<Model, ModelViewHolder<M>>(Model.DIFF_CALLBACK) {

    override fun getItemCount(): Int = modelList.size

    override fun getItemViewType(position: Int): Int = modelList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<M> {
        return ModelViewHolderMapper.map(
            parent,
            CellType.values()[viewType],
            viewModel,
            customResourcesProvider
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ModelViewHolder<M>, position: Int) {
        with(holder) {
            bindData(modelList[position] as M)
            bindViews(modelList[position] as M, adapterListener)
        }
    }

    override fun submitList(list: MutableList<Model>?) {
        list?.let { modelList = it }
        super.submitList(list)
    }

}