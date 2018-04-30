package com.simple.tv.app

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class BaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics())
    }
}
