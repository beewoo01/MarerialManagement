package com.raonsoft.matmanagement.screen.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    abstract val viewModel : VM

    protected lateinit var binding : VB

    private lateinit var fetchJob : Job

    abstract fun getViewBinding() : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        initState()
    }

    open fun initState() {
        Log.wtf("BaseActivity", "initState")
        initViews()
        fetchJob = viewModel.fetchData()
        observeData()
    }


    open fun initViews() = Unit

    abstract fun observeData()

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
}