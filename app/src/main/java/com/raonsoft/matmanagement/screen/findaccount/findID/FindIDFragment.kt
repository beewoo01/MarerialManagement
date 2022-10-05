package com.raonsoft.matmanagement.screen.findaccount.findID

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.FragmentFindIdBinding
import com.raonsoft.matmanagement.screen.base.BaseFragment
import com.raonsoft.matmanagement.screen.dialog.DefaultDialog


class FindIDFragment : BaseFragment<FindIDViewModel, FragmentFindIdBinding>() {
    override val viewModel: FindIDViewModel by viewModels()

    override fun getViewBinding(): FragmentFindIdBinding =
        FragmentFindIdBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.findIDState.observe(this@FindIDFragment) {
            //Log.wtf("FindIDFragment", "result is $it")
            val result =
                if (it == "계정정보를 찾을수없습니다.") {
                    it
                } else {
                    "아이디는 $it 입니다."
                }

            DefaultDialog(
                context = requireContext(),
                title = result,
                starText = "확인",
                endText = "취소"
            ) {}.show()
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        findIDButton.setOnClickListener {
            validation()
        }
    }


    private fun validation() = with(binding) {
        if (idEdit.text.toString().isEmpty()) {
            showToast("소속부대명을 입력해주세요.")

        } else if (groupEdit.text.toString().isEmpty()) {
            showToast("소속부대명 고유번호를 입력해주세요.")
        } else {
            viewModel.findIDState(idEdit.text.toString(), groupEdit.text.toString().toInt())
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}