package com.raonsoft.matmanagement.widget

import android.app.Application
import android.content.Context

class BaseProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}