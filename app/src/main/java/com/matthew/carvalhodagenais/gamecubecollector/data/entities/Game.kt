package com.matthew.carvalhodagenais.gamecubecollector.data.entities

import androidx.room.*
import com.matthew.carvalhodagenais.gamecubecollector.data.DateConverter
import java.util.*

@Entity(tableName = "game_table",
    indices = [Index("region_id"), Index("condition_id")],
    foreignKeys = [ForeignKey(
        entity = Region::class,
        parentColumns = ["id"],
        childColumns = ["region_id"],
        onDelete = ForeignKey.NO_ACTION),
    ForeignKey(
        entity = Condition::class,
        parentColumns = ["id"],
        childColumns = ["condition_id"],
        onDelete = ForeignKey.NO_ACTION)
    ])
@TypeConverters(DateConverter::class)
//Note: use named parameters instead of Builder. This is Kotlin!
data class Game(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "developer") var developers: String? = null, //DevOne::DevTwo if multiple
    @ColumnInfo(name = "publisher") var publishers: String? = null, //PubOne::PubTwo if multiple
    @ColumnInfo(name = "date_released") var releaseDate: Date? = null,
    @ColumnInfo(name = "region_id") var regionId: Int? = null,
    @ColumnInfo(name = "date_bought") var boughtDate: Date? = null,
    @ColumnInfo(name = "condition_id") var conditionId: Int? = null,
    @ColumnInfo(name = "price_paid") var pricePaid: Double? = null,
    @ColumnInfo(name = "case_in_box") var hasCase: Boolean? = null,
    @ColumnInfo(name = "includes_manual") var hasManual: Boolean? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "favourite")
    var isFavourite: Boolean? = false

    @ColumnInfo(name = "image_path")
    var imagePath: String? = ""
}