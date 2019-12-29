package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.*

//Available regions: NTSC-U, NTSC-J, PAL
@Entity(tableName = "region_table")
data class Region(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "region_code") var code: String,
    @ColumnInfo(name = "region_name")var name: String?
)