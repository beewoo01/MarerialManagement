package com.raonsoft.matmanagement.screen.join

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityJoinBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.dialog.DefaultDialog

class JoinActivity : BaseActivity<JoinViewModel, ActivityJoinBinding>() {
    override val viewModel: JoinViewModel by viewModels()

    override fun getViewBinding(): ActivityJoinBinding =
        ActivityJoinBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.signUpState.observe(this@JoinActivity) {
            val title =
            when (it) {
                0 -> "회원가입에 성공하였습니다."
                1 -> "이미 가입된 아이디입니다."
                2 -> "이미 가입된 부대명이 있습니다."
                3 -> "이미 가입된 부대입니다."
                else -> "회원가입중 오류가 발생하였습니다."
            }

            DefaultDialog(this@JoinActivity, title, "확인", "닫기") {
                finish()
            }.show()
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        signUpButton.setOnClickListener {
            validation()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun validation() = with(binding) {

        when {
            idEdit.text.toString().length <= 4 -> {
                showToast("아이디는 5자 이상등록해주세요.")
            }

            pwEdit.text.toString().length <= 7 -> {
                showToast("비밀번호는 8자 이상등록해주세요.")
            }

            pwEdit.text.toString() != pwConfirmEdit.text.toString() -> {
                showToast("비밀번호가 일치하지 않습니다.")
            }

            armyUnitNameEdit.text.toString().isEmpty() -> {
                showToast("부대명을 입력해주세요.")
            }

            divisionEdit.text.toString().isEmpty() -> {
                showToast("소속사단을 입력해주세요.")
            }

            regimentEdit.text.toString().isEmpty() -> {
                showToast("소속연대를 입력해주세요.")
            }

            battalionEdit.text.toString().isEmpty() -> {
                showToast("소속대대를 입력해주세요.")
            }

            else -> {
                viewModel.signUpAdmin(
                    account_id = idEdit.text.toString(),
                    account_password = pwEdit.text.toString(),
                    armyunit_name = armyUnitNameEdit.text.toString(),
                    armyunit_division = divisionEdit.text.toString(),
                    armyunit_regiment = regimentEdit.text.toString(),
                    armyunit_battalion = battalionEdit.text.toString()
                )
            }

        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@JoinActivity, msg, Toast.LENGTH_SHORT).show()
    }

}