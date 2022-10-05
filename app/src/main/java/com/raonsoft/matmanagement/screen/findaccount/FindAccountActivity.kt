package com.raonsoft.matmanagement.screen.findaccount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raonsoft.matmanagement.databinding.ActivityFindAccountBinding
import com.raonsoft.matmanagement.screen.findaccount.findID.FindIDFragment
import com.raonsoft.matmanagement.screen.findaccount.findPW.FindPWFragment

class FindAccountActivity : AppCompatActivity() {

    private var binding: ActivityFindAccountBinding? = null

    private val findAccountIDFragment: FindIDFragment by lazy {
        FindIDFragment()
    }

    private val findAccountPWFragment: FindPWFragment by lazy {
        FindPWFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
    }

    private fun initViews() = with(binding!!) {

        toolbar.setNavigationOnClickListener {
            finish()
        }

        viewPager2.adapter =
            ViewpagerAdapter(fragmentManager = supportFragmentManager, lifecycle = lifecycle)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = if (position == 0) {
                "아이디 찾기"
            } else {
                "비밀번호 찾기"
            }

        }.attach()

    }


    inner class ViewpagerAdapter(
        fragmentManager: FragmentManager, lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> findAccountIDFragment
                else -> findAccountPWFragment
            }
        }
    }
}