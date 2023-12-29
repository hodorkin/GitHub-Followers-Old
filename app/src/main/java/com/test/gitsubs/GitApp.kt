package com.test.gitsubs

import androidx.multidex.MultiDexApplication
import com.test.gitsubs.di.AppComponent
import com.test.gitsubs.di.DaggerAppComponent
import com.test.gitsubs.di.StorageModule

class GitApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .storageModule(StorageModule(this)).build()
    }

    companion object {
        var component: AppComponent? = null
            private set
    }
}