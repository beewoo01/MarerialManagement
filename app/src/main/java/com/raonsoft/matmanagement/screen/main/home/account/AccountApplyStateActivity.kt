package com.raonsoft.matmanagement.screen.main.home.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityAccountApplyStateBinding
import com.raonsoft.matmanagement.model.AccountStateModel
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.dialog.DefaultBottomSheetDialog
import com.raonsoft.matmanagement.screen.main.home.HomeViewModel
import com.raonsoft.matmanagement.util.AccountInfoSingleton
import com.raonsoft.matmanagement.util.provider.DefaultCustomResourcesProvider
import com.raonsoft.matmanagement.widget.adapter.LimitModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.ModelRecyclerAdapter
import com.raonsoft.matmanagement.widget.adapter.listener.AccountStateListener

class AccountApplyStateActivity :
    BaseActivity<AccountApplyStateViewModel, ActivityAccountApplyStateBinding>() {

    override val viewModel: AccountApplyStateViewModel by viewModels()

    override fun getViewBinding(): ActivityAccountApplyStateBinding =
        ActivityAccountApplyStateBinding.inflate(layoutInflater)

    private val resourcesProvider: DefaultCustomResourcesProvider by lazy {
        DefaultCustomResourcesProvider(this@AccountApplyStateActivity)
    }

    private val accountApplyAdapter by lazy {
        ModelRecyclerAdapter<AccountStateModel, AccountApplyStateViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            object : AccountStateListener {

                override fun onItemClick(model: AccountStateModel) {
                    DefaultBottomSheetDialog("허용", "거절") {
                        if (it) {

                        }
                    }.show(supportFragmentManager, null)
                }

            }
        )
    }

    override fun observeData() {
        viewModel.accountApplyList.observe(this@AccountApplyStateActivity) {
            it?.let { list ->
                accountApplyAdapter.submitList(list.toMutableList())
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        AccountInfoSingleton.account_idx?.let {
            viewModel.getAccountApplyState(it)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

        accountManagementRecyclerView.adapter = accountApplyAdapter


    }
}