package com.redfin.redfin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RedFinApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
