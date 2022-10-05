package com.raonsoft.matmanagement.screen.product

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.databinding.ActivityProductBinding
import com.raonsoft.matmanagement.extention.load
import com.raonsoft.matmanagement.model.CurrentProductContentModel
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.screen.editProduct.EditProductActivity
import com.raonsoft.matmanagement.screen.main.addProduct.AddProductActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import com.raonsoft.matmanagement.util.provider.DefaultCustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.ModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.listener.ProductOfItemListener

class ProductActivity : BaseActivity<ProductViewModel, ActivityProductBinding>() {

    override val viewModel: ProductViewModel by viewModels()

    private val itemIdx by lazy {
        intent.getIntExtra("itemIdx", 0)
    }

    private val itemName by lazy {
        intent.getStringExtra("itemName")
    }

    private val itemImage by lazy {
        intent.getStringExtra("itemImage")
    }

    override fun getViewBinding(): ActivityProductBinding =
        ActivityProductBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.productOfItemList.observe(this@ProductActivity) {
            it?.let { list ->
                productsAdapter.submitList(list.toMutableList())
            }
        }

        viewModel.deleteProductState.observe(this@ProductActivity) {
            it?.let { result ->
                if (result > 0) {
                    Toast.makeText(this@ProductActivity, "자재를 삭제에 성공했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            } ?: kotlin.run {
                Toast.makeText(this@ProductActivity, "자재를 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun initViews() = with(binding) {
        super.initViews()


        titleTxv.text = itemName

        Glide.with(itemImv.context)
            .load(DefaultUrl.DEFAULT_IMAGE_URL + itemImage)
            .apply(RequestOptions().circleCrop())
            .error(R.drawable.img6).into(itemImv)

        productsRecyclerView.adapter = productsAdapter

        toolbar.setNavigationOnClickListener {
            finish()
        }

        addProductButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ProductActivity,
                    AddProductActivity::class.java
                ).putExtra("itemIdx", itemIdx)
            )
        }

        if (itemIdx > 0) {
            viewModel.getProductOfItemIdx(itemIdx)
        }
    }

    private val resourcesProvider: DefaultCustomResourcesProvider by lazy {
        DefaultCustomResourcesProvider(this@ProductActivity)
    }

    private val productsAdapter by lazy {
        ModelRecyclerAdapter<CurrentProductContentModel, ProductViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            object : ProductOfItemListener {
                override fun onItemClick(model: CurrentProductContentModel) {
                    Log.wtf("productsAdapter", "onItemClick")
                    DefaultBottomSheetDialog(startButtonText = "수정", endButtonText = "삭제") {
                        if (it) {
                            val intent = Intent(
                                this@ProductActivity,
                                EditProductActivity::class.java
                            )

                            Log.w("model.product_idx", model.product_idx.toString())

                            intent.putExtra("product", model)

                            startActivity(intent)

                        } else {

                            viewModel.deleteProduct(item_idx = itemIdx)

                        }
                    }.show(supportFragmentManager, "")
                }

            }
        )
    }
}