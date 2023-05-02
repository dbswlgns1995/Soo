package com.jihoonyoon.soo.calendar.model

import com.jihoonyoon.soo.notepad.database.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*


@Table(database = AppDatabase::class, allFields = true)
class Diet() : BaseModel() {
    @PrimaryKey(autoincrement = true)
    @Column
    var uid: Long = 0L

    @Column
    var calendar: Calendar = Calendar.getInstance()
    @Column
    var menu1: String = ""
    @Column
    var menu2: String = ""
    @Column
    var menu3: String = ""
    @Column
    var backgroundColor: Int = 0
    @Column
    var textColor: Int = 0

}