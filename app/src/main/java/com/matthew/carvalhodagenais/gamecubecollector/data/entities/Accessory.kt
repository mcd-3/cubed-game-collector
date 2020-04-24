package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.*

@Entity(tableName = "accessory_table",
    indices = [
        Index("condition_id")],
    foreignKeys = [
        ForeignKey(
            entity = Condition::class,
            parentColumns = ["id"],
            childColumns = ["condition_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class Accessory(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "condition_id") var conditionId: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "image_path")
    var imageName: String? = ""
}
