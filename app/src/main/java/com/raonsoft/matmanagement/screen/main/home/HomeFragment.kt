package com.raonsoft.matmanagement.screen.main.home

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.data.url.DefaultUrl
import com.raonsoft.matmanagement.databinding.FragmentHomeBinding
import com.raonsoft.matmanagement.extention.load
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.model.ItemOfProductsModel
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.screen.itemEdit.ItemEditActivity
import com.raonsoft.matmanagement.screen.main.home.account.AccountApplyStateActivity
import com.raonsoft.matmanagement.screen.product.ProductActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import com.raonsoft.matmanagement.util.provider.DefaultCustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.LimitModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.listener.AccountStateListener
import com.raonsoft.matmanagement.widget.adapter.listener.ItemOfProductsListener


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    private val resourcesProvider: DefaultCustomResourcesProvider by lazy {
        DefaultCustomResourcesProvider(requireContext())
    }

    private val accountApplyAdapter by lazy {
        LimitModelRecyclerAdapter<AccountStateModel, HomeViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            object : AccountStateListener {

                override fun onItemClick(model: AccountStateModel) {
                    DefaultBottomSheetDialog("허용", "거절") {
                        if (it) {

                        }
                    }.show(requireActivity().supportFragmentManager, null)
                }

            }
        )
    }

    private val itemOfProductAdapter by lazy {
        LimitModelRecyclerAdapter<ItemOfProductsModel, HomeViewModel>(
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
                            deleteItem(model)
                        }
                    }.show(requireActivity().supportFragmentManager, null)
                }

            }
        )
    }

    private fun deleteItem(model: ItemOfProductsModel) {
        //TODO Item 삭제

    }

    override fun observeData() {
        viewModel.accountState.observe(this@HomeFragment) {
            var accountProfile: String? = null
            Log.wtf("accountState result ", it.toString())

            it?.let { map ->
                accountProfile = map["account_profile"].toString()

                AccountInfoSingleton.setAccountInfo(
                    armyunit_idx = map["accoundArmyUnit_armyunit_idx"].toString().toDouble()
                        .toInt(),
                    armyunit_name = map["armyunit_name"].toString(),
                    account_profile = accountProfile
                )

                with(binding) {
                    Glide.with(binding.profileImv.context)
                        .load(DefaultUrl.DEFAULT_IMAGE_URL + accountProfile)
                        .apply(RequestOptions().circleCrop())
                        .error(R.drawable.img6).into(binding.profileImv)

                    /*profileImv.load(
                        url = accountProfile,
                        corner = 100F,
                        defaultImage = R.drawable.ic_default_profile
                    )*/

                    groupName.text = map["armyunit_name"].toString()
                }

                viewModel.getAccountApplyState(
                    map["accoundArmyUnit_armyunit_idx"].toString().toDouble().toInt()
                )
            }


        }

        viewModel.accountApplyList.observe(this@HomeFragment) {
            it?.let { list ->
                accountApplyAdapter.submitList(list.toMutableList())
            }
        }

        viewModel.productCountOfItems.observe(this@HomeFragment) {
            it?.let { list ->
                itemOfProductAdapter.submitList(list.toMutableList())
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        accountApplyRecyclerView.adapter = accountApplyAdapter
        itemRecyclerView.adapter = itemOfProductAdapter

        AccountInfoSingleton.account_idx?.let {
            viewModel.getAccountInfo(it)
            viewModel.getProductCountOfItems(it)
        }

        moreItemTxv.setOnClickListener {
            findNavController().navigate(R.id.itemManagement)

        }

        moreAccountApplyStateTxv.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    AccountApplyStateActivity::class.java
                )
            )
        }


    }

}