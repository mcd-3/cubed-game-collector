package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Condition

@Dao
interface ConditionDao {

    @Insert
    suspend fun insert(condition: Condition)

}