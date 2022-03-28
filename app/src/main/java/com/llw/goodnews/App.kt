package com.llw.goodnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * App
 * @author llw
 */
class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}