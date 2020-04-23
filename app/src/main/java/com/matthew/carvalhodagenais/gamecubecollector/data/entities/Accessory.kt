package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.*

@Entity(tableName = "accessory_table",
    indices = [
        Index("region_id"),
        Index("condition_id")],
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["region_id"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = Condition::class,
            parentColumns = ["id"],
            childColumns = ["condition_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class Accessory(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "region_id") var regionId: Int?,
    @ColumnInfo(name = "condition_id") var conditionId: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "image_path")
    var imageName: String? = ""
}
