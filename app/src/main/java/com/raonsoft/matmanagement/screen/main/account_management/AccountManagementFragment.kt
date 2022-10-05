package com.raonsoft.matmanagement.screen.main.account_management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.FragmentAccountManagementBinding
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.screen.dialog.DefaultDialog
import com.raonsoft.matmanagement.screen.main.home.HomeViewModel
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import com.raonsoft.matmanagement.util.provider.DefaultCustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.LimitModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.ModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.listener.AccountStateListener

class AccountManagementFragment
    : BaseFragment<AccountManagementViewModel, FragmentAccountManagementBinding>() {

    override val viewModel: AccountManagementViewModel by viewModels()

    override fun getViewBinding(): FragmentAccountManagementBinding =
        FragmentAccountManagementBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.accountList.observe(this@AccountManagementFragment) {
            it?.let {
                accountAdapter.submitList(it.toMutableList())
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        AccountInfoSingleton.account_idx?.let {
            viewModel.getAccountApplyState(it)

        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        accountManagementRecyclerView.adapter = accountAdapter

    }

    private val resourcesProvider: DefaultCustomResourcesProvider by lazy {
        DefaultCustomResourcesProvider(requireContext())
    }

    private val accountAdapter by lazy {
        ModelRecyclerAdapter<AccountStateModel, AccountManagementViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            object : AccountStateListener {

                override fun onItemClick(model: AccountStateModel) {
                    /*context: Context,
                    private val title : String,
                    private val starText: String,
                    private val endText : String,
                    private val callBack: (Boolean) -> Unit*/
                    DefaultDialog(
                        requireContext(),
                        "'${model.account_name}'님을 퇴출 하시겠습니까?",
                        "확인",
                        "취소"
                    ) {

                        if (it) {

                        } else {

                        }

                    }.show()
                }

            }
        )
    }


}