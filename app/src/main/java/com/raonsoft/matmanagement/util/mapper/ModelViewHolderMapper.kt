package com.raonsoft.matmanagement.util.mapper

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raonsoft.matmanagement.databinding.*
import com.raonsoft.matmanagement.model.CellType
import com.raonsoft.matmanagement.model.Model
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.viewholder.*

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        customResourcesProvider: CustomResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.EMPTY_CELL -> {
                Log.wtf("ModelViewHolderMapper", "CellType.EMPTY_CELL")

                EmptyViewHolder(
                    binding = ViewholderEmptyBinding.inflate(inflater, parent, false),
                    viewModel = viewModel,
                    customResourcesProvider = customResourcesProvider
                )
            }

            CellType.ACCOUNT_CELL -> {
                AccountApplyStateViewHolder(
                    ViewholderAccountApplyStateBinding.inflate(inflater, parent, false),
                    viewModel,
                    customResourcesProvider
                )
            }

            CellType.ITEM_OF_PRODUCTS -> {
                ItemOfProductViewHolder(
                    binding = ViewholderItemBinding.inflate(inflater, parent, false),
                    viewModel = viewModel,
                    customResourcesProvider = customResourcesProvider
                )
            }

            CellType.CURRENT_PRODUCT_CONTENT -> {
                ProductOfItemViewHolder(
                    binding = ViewholderProductBinding.inflate(inflater, parent, false),
                    viewModel = viewModel,
                    customResourcesProvider = customResourcesProvider
                )
            }
        }

        return viewHolder as ModelViewHolder<M>
    }
}