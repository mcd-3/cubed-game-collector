package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Type

@Dao
interface TypeDao {

    @Insert
    fun insert(type: Type)

    @Query("SELECT COUNT(id) FROM type_table")
    fun getCount(): Int
}