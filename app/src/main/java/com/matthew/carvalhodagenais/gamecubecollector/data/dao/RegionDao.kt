package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Region

@Dao
interface RegionDao {

    @Insert
    fun insert(region: Region)

    @Update
    fun update(region: Region)

    @Delete
    fun delete(region: Region)

    @Query("SELECT * FROM region_table WHERE region_code = :code")
    fun getRegionByCode(code: String): LiveData<Region>

    @Query("SELECT * FROM region_table WHERE rowid = :id")
    fun getRegionById(id: Int): LiveData<Region>

    @Query("SELECT * FROM region_table")
    fun getRegions(): LiveData<List<Region>>
}