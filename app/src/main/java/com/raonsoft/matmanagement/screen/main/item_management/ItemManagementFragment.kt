package com.raonsoft.matmanagement.screen.main.item_management

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raonsoft.matmanagement.databinding.FragmentItemManagementBinding
import com.raonsoft.matmanagement.model.ItemOfProductsModel
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.screen.itemEdit.ItemEditActivity
import com.raonsoft.matmanagement.screen.main.item_management.addItem.AddItemActivity
import com.raonsoft.matmanagement.screen.product.ProductActivity
import com.raonsoft.matmanagement.screen.productManagement.ProductManagementActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import com.raonsoft.matmanagement.util.provider.DefaultCustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.LimitModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.ModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.listener.ItemOfProductsListener

class ItemManagementFragment
    : BaseFragment<ItemManagementViewModel, FragmentItemManagementBinding>() {

    override val viewModel: ItemManagementViewModel by viewModels()

    override fun getViewBinding(): FragmentItemManagementBinding =
        FragmentItemManagementBinding.inflate(layoutInflater)

    override fun observeData() {

        viewModel.productCountOfItems.observe(this@ItemManagementFragment) {
            it?.let { list ->

                itemOfProductAdapter.submitList(list.toMutableList())

            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        AccountInfoSingleton.account_idx?.let {
            viewModel.getProductCountOfItems(it)

        }

        itemRecyclerView.adapter = itemOfProductAdapter

        addProductTxv.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    AddItemActivity::class.java
                )
            )

        }

        disAndReButton.setOnClickListener {

            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    ProductManagementActivity::class.java
                )
            )

        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private val resourcesProvider: DefaultCustomResourcesProvider by lazy {
        DefaultCustomResourcesProvider(requireContext())
    }

    private val itemOfProductAdapter by lazy {
        ModelRecyclerAdapter<ItemOfProductsModel, ItemManagementViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            object : ItemOfProductsListener {


                override fun onItemClick(model: ItemOfProductsModel) {
                    val intent = Intent(requireActivity(), ProductActivity::class.java)
                    intent.putExtra("itemIdx", model.item_idx)
                    intent.putExtra("itemName", model.item_name)
                    intent.putExtra("itemImage", model.item_image)
                    startActivity(intent)
                }

                override fun onItemStateClick(model: ItemOfProductsModel) {
                    DefaultBottomSheetDialog("수정", "삭제") {
                        if (it) {

                            val intent = Intent(requireActivity(), ItemEditActivity::class.java)
                            intent.putExtra("itemIdx", model.item_idx)
                            intent.putExtra("itemName", model.item_name)
                            intent.putExtra("itemImage", model.item_image)
                            startActivity(intent)

                        } else {
                            //deleteItem(model)
                        }
                    }.show(requireActivity().supportFragmentManager, null)
                }

            }
        )
    }

}