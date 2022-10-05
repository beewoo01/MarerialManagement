package com.raonsoft.matmanagement.screen.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivityMainBinding
import com.raonsoft.matmanagement.screen.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val navController: NavController by lazy {

        (supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
    }

    private fun initViews() = with(binding!!) {

        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment
        val navController = navHostFragment.navController*/
        navController.setGraph(R.navigation.nav_graph)
        // 바텀 네비게이션 뷰와 navController 연결

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.wtf("MainActivity", "addOnDestinationChangedListener")
        }

    }


    override fun onBackPressed() {
        Log.wtf("MainActivity", "onBackPressed")
        //super.onBackPressed()
        Log.wtf("MainActivity", "label is " + navController.currentDestination?.label)
        val str : String = resources.getString(R.string.home)
        if (navController.currentDestination?.label == str) {
            finish()
        } else {
            navController.popBackStack()
        }
        Log.wtf("MainActivity", "onBackPressed")
        /*if (!navController.popBackStack()) {

            this@MainActivity.finish()
            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))

            Log.wtf("MainActivity", "!navController.popBackStack()111")
        }*/

        /*if (!navController.popBackStack()) {
            Log.wtf("MainActivity", "!navController.popBackStack()111")
        } else {
            Log.wtf("MainActivity", "navController.popBackStack()222")
        }

        if (navController.currentDestination?.label == "홈") {
            Log.wtf("MainActivity", "id is R.id.home")
        } else {
            Log.wtf("MainActivity", "id is else ")
            Log.wtf("MainActivity", "label is ${navController.currentDestination?.label}")
        }

        Toast.makeText(this@MainActivity, "onBackPressed", Toast.LENGTH_SHORT).show()*/
    }
}