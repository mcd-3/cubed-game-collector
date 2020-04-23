package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory

@Dao
interface AccessoryDao {

    @Insert
    suspend fun insert(accessory: Accessory)

    @Update
    suspend fun update(accessory: Accessory)

    @Delete
    suspend fun delete(accessory: Accessory)

    @Query("DELETE FROM accessory_table")
    suspend fun deleteAllAccessories()

    @Query("SELECT * FROM accessory_table")
    fun getAllAccessories(): LiveData<List<Accessory>>

}