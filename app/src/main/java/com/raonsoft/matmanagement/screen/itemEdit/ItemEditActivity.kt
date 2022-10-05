package com.raonsoft.matmanagement.screen.itemEdit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityAddItemBinding
import com.raonsoft.matmanagement.databinding.ActivityItemEditBinding
import com.raonsoft.matmanagement.screen.base.BaseActivity
import com.raonsoft.matmanagement.screen.main.item_management.addItem.AddItemViewModel

class ItemEditActivity : BaseActivity<ItemEditViewModel, ActivityItemEditBinding>() {

    override val viewModel: ItemEditViewModel by viewModels()

    override fun getViewBinding(): ActivityItemEditBinding =
        ActivityItemEditBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun initViews() = with(binding){
        super.initViews()

        editButton.setOnClickListener {
            validation()
        }
    }

    private fun validation() = with(binding){
        if (battalionEdit.text.isEmpty()) {
            Toast.makeText(this@ItemEditActivity, "품목명을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel
        }
    }

}