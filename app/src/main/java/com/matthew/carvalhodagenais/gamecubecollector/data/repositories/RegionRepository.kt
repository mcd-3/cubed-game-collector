package com.matthew.carvalhodagenais.gamecubecollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.RegionDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Region

class RegionRepository(application: Application) {

    private var regionDao: RegionDao
    private var allRegions: LiveData<List<Region>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        regionDao = database.regionDao()
        allRegions = regionDao.getRegions()
    }

    fun getAllRegion(): LiveData<List<Region>> {
        return allRegions
    }

    fun getRegionCodes(): List<String> {
        return regionDao.getRegionCodes()
    }

}