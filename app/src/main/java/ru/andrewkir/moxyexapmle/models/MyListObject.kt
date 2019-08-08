package ru.andrewkir.moxyexapmle.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class MyListObject(): RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var _id: Long = 0

    var title:String? = null
    var content:String? = null
}