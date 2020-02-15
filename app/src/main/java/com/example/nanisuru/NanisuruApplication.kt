package com.example.nanisuru

import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.regex.Pattern

class NanisuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // migrationの設定を読み込む処理
        val builder = RealmConfiguration.Builder()
        builder.schemaVersion((1L)).migration(Migration())
        val config = builder.build()
        Realm.setDefaultConfiguration(config)

        // Stethoの設定
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build())

    }
}