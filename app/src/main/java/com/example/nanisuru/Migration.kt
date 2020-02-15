package com.example.nanisuru

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val realmSchema = realm.schema
        var oldVersion = oldVersion

        if ( oldVersion == 0L ) {
            realmSchema.get("Item")?.addField("isPrivate", Boolean::class.java)
            oldVersion++
        }
    }
}