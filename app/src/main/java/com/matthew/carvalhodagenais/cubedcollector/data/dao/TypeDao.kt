package com.matthew.carvalhodagenais.cubedcollector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Type

@Dao
interface TypeDao {

    @Insert
    suspend fun insert(type: Type)

    @Query("SELECT COUNT(id) FROM type_table")
    fun getCount(): Int
}