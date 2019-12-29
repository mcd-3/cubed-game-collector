package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Available types: Disc, Case, Manual, Console, Accessory
@Entity(tableName = "type_table")
data class Type(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "code") var code: String,
    @ColumnInfo(name = "type") var type: String
) {
}