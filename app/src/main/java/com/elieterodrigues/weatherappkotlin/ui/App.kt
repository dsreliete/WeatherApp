package com.elieterodrigues.weatherappkotlin.ui

import android.app.Application

class App : Application() {

//    The problem with this solution is that we could change the value of this instance from anywhere
//    in the App, because a var property is required if we want to use lateinit.
//    That’s easy to solve by using a private set:

    companion object {
        lateinit var instance: Application
            private set
    }

        override fun onCreate() {
        super.onCreate()
        instance = this
    }
}