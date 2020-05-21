package com.matthew.carvalhodagenais.cubedcollector.data.entities

import androidx.room.*

//Available regions: NTSC-U, NTSC-J, PAL
@Entity(tableName = "region_table")
data class Region(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "region_code") var code: String,
    @ColumnInfo(name = "region_name")var name: String?
) {
    companion object {
        /**
         * Gets the region name by ID without having to query the database
         *
         * @param id Int
         * @return String
         */
        fun getRegionName(id: Int?): String {
            return when(id) {
                1 -> "NTSC-U"
                2 -> "PAL"
                3 -> "NTSC-J"
                else -> "No Region"
            }
        }
    }
}