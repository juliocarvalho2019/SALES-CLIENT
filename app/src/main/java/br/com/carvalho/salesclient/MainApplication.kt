package br.com.carvalho.salesclient

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    init {
        instance = this
    }

    //Singleton kotlin
    companion object {
        private var instance: MainApplication? = null
        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }

    }
}