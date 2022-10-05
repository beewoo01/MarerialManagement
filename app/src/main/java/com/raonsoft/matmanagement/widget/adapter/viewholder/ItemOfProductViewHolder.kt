package com.raonsoft.matmanagement.widget.adapter.viewholder

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.databinding.ViewholderAccountApplyStateBinding
import com.raonsoft.matmanagement.databinding.ViewholderItemBinding
import com.raonsoft.matmanagement.model.ItemOfProductsModel
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener
import com.raonsoft.matmanagement.widget.adapter.listener.ItemOfProductsListener

class ItemOfProductViewHolder(
    private val binding: ViewholderItemBinding,
    viewModel : BaseViewModel,
    customResourcesProvider: CustomResourcesProvider
)
    : ModelViewHolder<ItemOfProductsModel>(binding, viewModel, customResourcesProvider) {
    override fun reset() = Unit

    @SuppressLint("SetTextI18n")
    override fun bindData(model: ItemOfProductsModel) {
        super.bindData(model)

        with(binding) {
            Glide.with(itemImageView.context)
                .load(DefaultUrl.DEFAULT_IMAGE_URL + model.item_image)
                .circleCrop()
                .placeholder(R.drawable.img6)
                .error(R.drawable.img6)
                .into(itemImageView)

            itemNameTxv.text = model.item_name
            countTxv.text = "${model.remainingProduct} / ${model.allCount}"
        }
    }

    override fun bindViews(model: ItemOfProductsModel, adapterListener: AdapterListener?) {
        with(binding) {

            moreImb.setOnClickListener {
                if (adapterListener is ItemOfProductsListener) {
                    adapterListener.onItemStateClick(model)
                }
            }

            root.setOnClickListener {
                if (adapterListener is ItemOfProductsListener) {
                    adapterListener.onItemClick(model)
                }
            }
        }
    }
}