package com.raonsoft.matmanagement.widget.adapter.viewholder

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ViewholderProductBinding
import com.raonsoft.matmanagement.extention.setNullCheckText
import com.raonsoft.matmanagement.model.CurrentProductContentModel
import com.raonsoft.matmanagement.screen.base.BaseViewModel
import com.raonsoft.matmanagement.util.provider.CustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.listener.AdapterListener
import com.raonsoft.matmanagement.widget.adapter.listener.ProductOfItemListener

class ProductOfItemViewHolder(
    private val binding: ViewholderProductBinding,
    viewModel: BaseViewModel,
    customResourcesProvider: CustomResourcesProvider
) : ModelViewHolder<CurrentProductContentModel>(binding, viewModel, customResourcesProvider) {

    override fun reset() = Unit

    override fun bindViews(model: CurrentProductContentModel, adapterListener: AdapterListener?) {
        with(binding) {
            root.setOnClickListener {
                if (adapterListener is ProductOfItemListener) {
                    adapterListener.onItemClick(model)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindData(model: CurrentProductContentModel) = with(binding) {
        super.bindData(model)

        productNameTxv.text = model.product_name


        specialTxv.text = model.special
        dateTxv.text = model.provisionInfo_createtime

        when (model.product_status) {
            0 -> {
                stateTxv.apply {
                    text = "반납"
                    setTextColor(ContextCompat.getColor(context, R.color.main_color))
                    background = ContextCompat.getDrawable(
                        stateTxv.context,
                        R.drawable.bg_full_round_sub_color
                    )
                }

                dateTxv.isVisible = false
                accountInfoTxv.isVisible = false
                line.background = ContextCompat.getDrawable(line.context, R.color.main_color)
            }

            1 -> {
                stateTxv.apply {
                    text = "불출"
                    setTextColor(ContextCompat.getColor(context, R.color.point_brown_color))
                    background = ContextCompat.getDrawable(
                        stateTxv.context,
                        R.drawable.bg_full_round_tin_brown
                    )
                }

                dateTxv.setNullCheckText(listOf(model.provisionInfo_createtime))
                dateTxv.isVisible = true
                accountInfoTxv.isVisible = true
                accountInfoTxv.setNullCheckText(listOf(model.provisionInfo_company, model.provisionInfo_platoon, model.provisionInfo_user_name))
                line.background = ContextCompat.getDrawable(line.context, R.color.point_brown_color)

                /*accountInfoTxv.text =
                    "${model.provisionInfo_company} ${model.provisionInfo_platoon} ${model.provisionInfo_user_name}"*/

            }

            2 -> {
                stateTxv.apply {
                    text = "폐기"
                    setTextColor(ContextCompat.getColor(context, R.color.font_deep_gray_color))
                    background = ContextCompat.getDrawable(
                        stateTxv.context,
                        R.drawable.bg_full_round_tin_gray
                    )
                }

                dateTxv.isVisible = false
                accountInfoTxv.isVisible = false
                line.background =
                    ContextCompat.getDrawable(line.context, R.color.font_deep_gray_color)
            }
        }

    }

}