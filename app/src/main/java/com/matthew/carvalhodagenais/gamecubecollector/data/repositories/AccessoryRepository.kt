package com.matthew.carvalhodagenais.gamecubecollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.AccessoryDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory

class AccessoryRepository(application: Application) {

    private var accessoryDao: AccessoryDao
    private var allAccessories: LiveData<List<Accessory>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        accessoryDao = database.accessoryDao()
        allAccessories = accessoryDao.getAllAccessories()
    }

    suspend fun insertAccessory(accessory: Accessory) {
        accessoryDao.insert(accessory)
    }

    suspend fun updateAccessory(accessory: Accessory) {
        accessoryDao.update(accessory)
    }

    suspend fun deleteAccessory(accessory: Accessory) {
        accessoryDao.delete(accessory)
    }

    suspend fun deleteAllAccessories() {
        accessoryDao.deleteAllAccessories()
    }

    fun getAllAccessories(): LiveData<List<Accessory>> {
        return allAccessories
    }

}