package com.matthew.carvalhodagenais.cubedcollector.data.entities

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
) {
    companion object {
        /**
         * Gets the condition name by ID without having to query the database
         *
         * @param id Int
         * @return String
         */
        fun getConditionName(id: Int?): String {
            return when(id) {
                1 -> "Mint"
                2 -> "Near Mint"
                3 -> "Very Good"
                4 -> "Fair"
                5 -> "Poor"
                else -> "No Condition"
            }
        }
    }
}