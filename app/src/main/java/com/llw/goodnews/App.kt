package com.llw.goodnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.llw.goodnews.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

/**
 * App
 * @author llw
 */
@HiltAndroidApp
class App : Application() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var db: AppDatabase
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        db = AppDatabase.getInstance(context)
        instance = this
    }
}