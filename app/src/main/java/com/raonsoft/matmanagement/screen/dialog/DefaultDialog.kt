package com.raonsoft.matmanagement.screen.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.raonsoft.matmanagement.R

class DefaultDialog(
    context: Context,
    private val title: String,
    private val starText: String,
    private val endText: String,
    private val callBack: (Boolean) -> Unit
) : Dialog(context, R.style.FullScreenDialogStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_default)

        window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.AnimationPopupStyle
        }

        initViews()

    }

    private fun initViews() {

        findViewById<TextView>(R.id.titleTxv).apply {
            text = title
        }

        findViewById<Button>(R.id.startButton).apply {
            text = starText

            setOnClickListener {
                callBack(true)
                dismiss()
            }
        }


        findViewById<Button>(R.id.endButton).apply {
            text = endText

            setOnClickListener {
                callBack(false)
                dismiss()
            }
        }
    }

}