package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Region

@Dao
interface RegionDao {

    @Insert
    suspend fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)

    @Query("SELECT * FROM region_table WHERE region_code = :code")
    fun getRegionByCode(code: String): LiveData<Region>

    @Query("SELECT * FROM region_table WHERE rowid = :id")
    fun getRegionById(id: Int): LiveData<Region>

    @Query("SELECT * FROM region_table")
    fun getRegions(): LiveData<List<Region>>

    @Query("SELECT region_code FROM region_table")
    fun getRegionCodes(): List<String>
}