package com.matthew.carvalhodagenais.cubedcollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.cubedcollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.cubedcollector.data.dao.RegionDao
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Region
import kotlinx.coroutines.coroutineScope

class RegionRepository(application: Application) {

    private var regionDao: RegionDao
    private var allRegions: LiveData<List<Region>>
    private var allRegionCodes: LiveData<List<String>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        regionDao = database.regionDao()
        allRegions = regionDao.getRegions()
        allRegionCodes = regionDao.getRegionCodes()
    }

    fun getAllRegion(): LiveData<List<Region>> {
        return allRegions
    }

    fun getRegionCodes(): LiveData<List<String>> {
        return allRegionCodes
    }

    fun getRegionById(id: Int): LiveData<Region> {
        return regionDao.getRegionById(id)
    }

    suspend fun getRegionByCode(code: String): Region = coroutineScope{
        regionDao.getRegionByCodeAsync(code)
    }

}