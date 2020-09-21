package com.laily.globalprint.utils

import android.app.Application

class App : Application() {

    companion object {
        lateinit var prefs: SharedPrefs
        var activityListPelangganHarusDiRefresh : Boolean = false
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}