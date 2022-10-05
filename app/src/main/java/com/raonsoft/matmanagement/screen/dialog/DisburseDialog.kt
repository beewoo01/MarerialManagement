package com.raonsoft.matmanagement.screen.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.raonsoft.matmanagement.R

class DisburseDialog(
    context: Context,
    private val callback: (company: String, platoon: String, name: String, etc: String) -> Unit
) : Dialog(context, R.style.FullScreenDialogStyle) {

    private val companyList: MutableList<String> = mutableListOf("1중대", "2중대", "3중대", "4중대", "본부중대")
    private val platoonList: MutableList<String> = mutableListOf("1소대", "2소대", "3소대", "4소대", "기타")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_disburse)

        window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.AnimationPopupStyle
        }

        initViews()

    }

    private fun initViews() {

        findViewById<Spinner>(R.id.companySpn).apply {
            adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                companyList
            )

            setSelection(0)
        }


        findViewById<Spinner>(R.id.platoonSpn).apply {
            adapter = ArrayAdapter(
                this@DisburseDialog.context,
                android.R.layout.simple_spinner_dropdown_item,
                platoonList
            )

            setSelection(0)
        }


        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            dismiss()
        }

        findViewById<Button>(R.id.returnButton).setOnClickListener {
            validation()
        }

    }

    private fun validation() {
        val company : String = findViewById<Spinner>(R.id.companySpn).selectedItem.toString()
        val platoon : String = findViewById<Spinner>(R.id.platoonSpn).selectedItem.toString()
        val name: String = findViewById<EditText>(R.id.nameEdit).text.toString()
        val etc: String = findViewById<EditText>(R.id.etcEdt).text.toString()

        if (name.isEmpty()) {

            Toast.makeText(context, "불출받는분의 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return

        } else {
            callback(company, platoon, name,etc)
            dismiss()
        }
    }


    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        @SuppressLint("SetTextI18n")
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            (parent?.getChildAt(0) as TextView).run {
                text = parent.getItemAtPosition(position).toString()
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }


        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }
}