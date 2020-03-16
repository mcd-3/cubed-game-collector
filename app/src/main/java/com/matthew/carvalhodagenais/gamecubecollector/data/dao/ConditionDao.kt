package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Condition

@Dao
interface ConditionDao {

    @Insert
    suspend fun insert(condition: Condition)

    @Query("SELECT * FROM condition_table WHERE condition_code = :code AND type_id = :typeID")
    suspend fun getConditionByCodeAndTypeAsync(code: String, typeID: Int): Condition

    @Query("SELECT condition_code FROM condition_table INNER JOIN type_table ON condition_table.type_id = type_table.id WHERE type_table.id = :typeID")
    fun getConditionCodesByType(typeID: Int): LiveData<List<String>>

    @Query("SELECT * FROM condition_table")
    fun getAllConditions(): LiveData<List<Condition>>

    @Query("SELECT * FROM condition_table WHERE id = :id")
    fun getConditionById(id: Int): LiveData<Condition>

}