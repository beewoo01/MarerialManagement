package com.raonsoft.matmanagement.screen.main.addProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityAddProductBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton

class AddProductActivity : BaseActivity<AddProductViewModel, ActivityAddProductBinding>() {

    override val viewModel: AddProductViewModel by viewModels()

    override fun getViewBinding(): ActivityAddProductBinding =
        ActivityAddProductBinding.inflate(layoutInflater)

    private val itemIdx : Int by lazy {
        intent.getIntExtra("itemIdx", 0)
    }

    override fun observeData() {
        viewModel.registerProduct.observe(this@AddProductActivity) {
            it?.let { result ->
                if (result > 0) {
                    showToast("자재등록에 성공하였습니다.")
                    finish()

                } else {
                    showToast("자재등록에 실패하였습니다.")
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()


        registerButton.setOnClickListener {
            validation()
        }

    }

    private fun validation() = with(binding) {

        when {

            productNameEdit.text.toString().isEmpty() -> showToast("자제명을 입력해주세요.")

            tagInfoEdit.text.toString().isEmpty() -> showToast("태그정보를 입력해주세요.")

            else -> AccountInfoSingleton.account_idx?.let {
                val specialStr: String = specialEdit.text.toString().ifEmpty {
                    "특이사항 없음"
                }

                if (itemIdx <= 0 ) {
                    showToast("자재등록에 실패하였습니다.")
                    return@with
                }

                viewModel.registerProduct(
                    product_name = productNameEdit.text.toString(),
                    product_description = specialStr,
                    product_item_idx = itemIdx,
                    product_account_idx = it,
                    product_taginfo = tagInfoEdit.text.toString()
                )
            }

        }

    }

    private fun showToast(msg: String) {
        Toast.makeText(this@AddProductActivity, msg, Toast.LENGTH_SHORT).show()
    }
}