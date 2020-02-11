package com.example.nanisuru

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Item : RealmObject() {
    @PrimaryKey
    var id: Long = 0

    @Required
    var title: String = ""
    @Required
    var finished: Boolean = false
    @Required
    var private: Boolean = false

    var memo: String = ""
    var place: String = ""

}