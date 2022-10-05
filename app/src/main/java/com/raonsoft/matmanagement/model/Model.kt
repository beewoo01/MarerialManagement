package com.raonsoft.matmanagement.model

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

abstract class Model(
    open val uid : Long,
    open val type : CellType
) {

    companion object {
        val DIFF_CALLBACK : DiffUtil.ItemCallback<Model> = object : DiffUtil.ItemCallback<Model>() {
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem.uid == newItem.uid && oldItem.type == newItem.type
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem === newItem
            }

        }
    }
}