package com.simple.tv.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
    }
}
