package com.raonsoft.matmanagement.screen.editProduct

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityEditProductBinding
import com.raonsoft.matmanagement.model.CurrentProductContentModel
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.util.AccountInfoSingleton

class EditProductActivity : BaseActivity<EditProductViewModel, ActivityEditProductBinding>() {

    override val viewModel: EditProductViewModel by viewModels()

    override fun getViewBinding(): ActivityEditProductBinding =
        ActivityEditProductBinding.inflate(layoutInflater)

    private val product: CurrentProductContentModel? by lazy {
        intent.getParcelableExtra("product")
    }

    override fun observeData() {
        viewModel.updateProductState.observe(this@EditProductActivity) {
            it?.let {
                if (it > 0) {
                    showToast("자제 수정에 성공하였습니다.")
                    finish()

                } else {
                    showToast("자제 수정에 실패하였습니다.")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        super.initViews()

        with(binding) {

            product?.let {
                productNameEdit.setText(it.product_name)
                tagInfoEdit.setText(it.product_taginfo)
                specialEdit.setText(it.special)

                checkBox.isChecked = it.product_status == 2

                productStatusTxv.text = "${it.product_name}은 현재 ${
                    when (it.product_status) {
                        0 -> "반납상태입니다."

                        1 -> "불출상태입니다."

                        else -> {
                            "폐기상태입니다."
                        }
                    }
                }"

                editButton.setOnClickListener {
                    validation()
                }

            }

            toolbar.setNavigationOnClickListener {
                finish()
            }
        }

    }

    private fun validation() = with(binding) {
        when {
            productNameEdit.text.toString().isEmpty() -> showToast("자재명을 입력해주세요.")

            tagInfoEdit.text.toString().isEmpty() -> showToast("TAG정보를 입력해주세요.")

            else -> {
                product?.let { model ->
                    if (checkBox.isChecked && model.product_status == 1) {
                        showToast("불출상태에서는 폐기처분을 할 수 없습니다.")
                    } else {
                        AccountInfoSingleton.account_idx?.let {
                            viewModel.updateProduct(
                                it,

                                productNameEdit.text.toString(),

                                specialEdit.text.toString().ifEmpty {
                                    "특이사항 없음"
                                },

                                tagInfoEdit.text.toString(),

                                if (checkBox.isChecked) {
                                    2
                                } else if (model.product_status == 2) {
                                    0
                                } else {
                                    model.product_status
                                },

                                model.product_idx
                            )

                        }

                    }
                }

            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@EditProductActivity, msg, Toast.LENGTH_SHORT).show()
    }

}