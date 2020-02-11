package com.example.nanisuru

import android.app.Application
import io.realm.Realm

class NanisuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}