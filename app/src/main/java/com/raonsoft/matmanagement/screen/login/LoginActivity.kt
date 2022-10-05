package com.raonsoft.matmanagement.screen.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityLoginBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.findaccount.FindAccountActivity
import com.raonsoft.matmanagement.screen.join.JoinActivity
import com.raonsoft.matmanagement.screen.main.MainActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {


    override val viewModel: LoginViewModel by viewModels()
    //override val viewModel: SplashViewModel by viewModels()


    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun observeData() {

        viewModel.accountState.observe(this@LoginActivity) { result ->
            result?.let {
                Log.wtf("LoginActivity", "observeData result $it")

                when {
                    it > 0 -> {
                        AccountInfoSingleton.getInstance(account_idx = it)
                        moveAct(MainActivity::class.java)
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java)
                                .apply {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                        )

                        finish()
                    }

                    it <= 0 -> {
                        showToast("계정정보가 불일치합니다.")
                    }
                }

            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        loginButton.setOnClickListener {
            validation()
        }

        findAccountTxv.setOnClickListener {
            moveAct(FindAccountActivity::class.java)

        }

        signUpTxv.setOnClickListener {
            moveAct(JoinActivity::class.java)
        }


    }

    private fun moveAct(activity: Class<*>) {
        startActivity(Intent(this@LoginActivity, activity))
    }

    private fun validation() = with(binding) {

        if (idEdit.text.toString().isEmpty()) {
            showToast(msg = "아이디를 입력해주세요.")

        } else if (pwEdit.text.toString().isEmpty()) {
            showToast(msg = "비밀번호를 입력해주세요.")
        } else {
            viewModel.loginAccount(idEdit.text.toString(), pwEdit.text.toString(), 1)

        }
    }

    private fun showToast(msg: String) {

        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onRestart() {
        super.onRestart()
        Log.wtf("LoginActivity", "onRestart")
    }
}