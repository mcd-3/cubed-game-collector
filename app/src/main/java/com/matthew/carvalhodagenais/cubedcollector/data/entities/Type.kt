package com.matthew.carvalhodagenais.cubedcollector.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Available types: Disc, Console, Accessory
@Entity(tableName = "type_table")
data class Type(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "code") var code: String,
    @ColumnInfo(name = "type") var type: String
) {
    // kinda cheating but it's faster than accessing DB
    companion object {
        const val CD_ID = 1
        const val CONSOLE_ID = 2
        const val ACCESSORY_ID = 3
    }
}