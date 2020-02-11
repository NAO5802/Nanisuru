package com.example.nanisuru

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Item : RealmObject() {
    @PrimaryKey
    var id: Long = 0

    @Required
    var title: String = ""

    var memo: String = ""
    var place: String = ""
    var finished: Boolean = false
    var private: Boolean = false


}