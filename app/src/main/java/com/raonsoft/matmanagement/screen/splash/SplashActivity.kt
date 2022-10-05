package com.raonsoft.matmanagement.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.raonsoft.matmanagement.R
import com.raonsoft.matmanagement.databinding.ActivitySplashBinding
import com.raonsoft.matmanagement.screen.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var viewBinding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        initViews()
    }

    private fun initViews() = with(viewBinding!!) {

        fadeOutAnimation.setAnimationListener(fadeOutAnimationListener)
        val fadeInAnimation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.logo_fade_in)
        fadeInAnimation.setAnimationListener(fadeInAnimationListener)
        logoImv.startAnimation(fadeInAnimation)

    }

    private val fadeOutAnimation by lazy {
        AnimationUtils.loadAnimation(this@SplashActivity, R.anim.logo_fade_out)
    }

    private val fadeOutAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) = Unit

        override fun onAnimationEnd(p0: Animation?) {
            Log.wtf("SplashActivity", "onAnimationEnd")
            viewBinding?.logoImv?.visibility = View.GONE
            startActivity(
                Intent(this@SplashActivity, LoginActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }

            )

            finish()
        }

        override fun onAnimationRepeat(p0: Animation?) = Unit

    }

    private val fadeInAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) = Unit

        override fun onAnimationEnd(p0: Animation?) {
            Log.wtf("SplashActivity", "fadeInAnimationListener")
            viewBinding?.logoImv?.startAnimation(fadeOutAnimation)


        }

        override fun onAnimationRepeat(p0: Animation?) = Unit

    }
}