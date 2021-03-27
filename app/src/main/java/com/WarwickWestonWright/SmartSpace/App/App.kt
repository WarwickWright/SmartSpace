package com.WarwickWestonWright.SmartSpace.App

import android.app.Application

class App: Application() {

    private var action = 0
    //lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        application = this
        initSingletons()
    }

    fun initSingletons() {
        //db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "MyImageLib").build()
    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
    * */

    fun getRpcAction() : Int {
        return action
    }

    fun setRpcAction(action: Int) {
        this.action = action
    }

    companion object {
        @JvmStatic
        private lateinit var application: Application
        @JvmStatic
        fun getApp(): Application {
            return application
        }
    }

}