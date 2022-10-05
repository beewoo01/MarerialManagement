package com.raonsoft.matmanagement.screen.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.DefaultBottomSheetDialogBinding

class DefaultBottomSheetDialog(
    private val startButtonText: String,
    private val endButtonText: String,
    private val callBack: (Boolean) -> Unit
) : BottomSheetDialogFragment() {

    private var binding: DefaultBottomSheetDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DefaultBottomSheetDialogBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding!!) {
        agreeButton.apply {
            text = startButtonText

            setOnClickListener {
                callBack(true)
                dismiss()
            }
        }

        cancelButton.apply {

            text = endButtonText

            setOnClickListener {
                callBack(false)
                dismiss()
            }

        }

    }
}