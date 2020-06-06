package com.matthew.carvalhodagenais.cubedcollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.cubedcollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.cubedcollector.data.dao.AccessoryDao
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Accessory
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper

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
        if (accessory.imageName != null) {
            ImageStorageHelper.deleteImage(ImageStorageHelper.IMAGE_PATH, accessory.imageName.toString())
        }
        accessoryDao.delete(accessory)
    }

    suspend fun deleteAllAccessories() {
        accessoryDao.deleteAllAccessories()
    }

    fun getAllAccessories(): LiveData<List<Accessory>> {
        return allAccessories
    }

}