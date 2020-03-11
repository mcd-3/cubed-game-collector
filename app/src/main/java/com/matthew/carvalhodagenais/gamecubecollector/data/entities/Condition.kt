package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "condition_table",
    indices = [androidx.room.Index("type_id")],
    foreignKeys = [
        ForeignKey(
            entity = Type::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class Condition(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "condition_code") var code: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type_id") var type: Int
)