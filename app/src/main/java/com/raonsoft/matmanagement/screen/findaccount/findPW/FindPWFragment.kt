package com.raonsoft.matmanagement.screen.findaccount.findPW

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.FragmentFindPwBinding
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultDialog

class FindPWFragment : BaseFragment<FindPwViewModel, FragmentFindPwBinding>() {

    override val viewModel: FindPwViewModel by viewModels()

    override fun getViewBinding(): FragmentFindPwBinding =
        FragmentFindPwBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.findPWState.observe(this@FindPWFragment) {
            it?.let {
                val result =
                    if (it == "계정정보를 찾을수없습니다.") {
                        it
                    } else {
                        "비밀번호는 $it 입니다."
                    }

                DefaultDialog(
                    context = requireContext(),
                    title = result,
                    starText = "확인",
                    endText = "취소"
                ) {}.show()
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        findPWButton.setOnClickListener {
            validation()
        }
    }

    private fun validation() = with(binding) {
        if (idEdit.text.toString().isEmpty()) {
            showToast("소속부대명을 입력해주세요.")

        } else if (groupEdit.text.toString().isEmpty()) {
            showToast("소속부대 고유번호를 입력해주세요.")
        } else if (nameEdit.text.toString().isEmpty()) {
            showToast("소속부대명을 입력해주세요.")
        } else {
            viewModel.findPWState(
                account_name = nameEdit.text.toString(),
                accoundArmyUnit_armyunit_idx = groupEdit.text.toString().toInt(),
                account_id = idEdit.text.toString()

            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


}