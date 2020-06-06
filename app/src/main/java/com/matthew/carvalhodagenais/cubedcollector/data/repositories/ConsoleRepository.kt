package com.matthew.carvalhodagenais.cubedcollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.cubedcollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.cubedcollector.data.dao.ConsoleDao
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Console
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper

class ConsoleRepository(application: Application) {

    private var consoleDao: ConsoleDao
    private var allConsoles: LiveData<List<Console>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        consoleDao = database.consoleDao()
        allConsoles = consoleDao.getAllConsoles()
    }

    suspend fun insertConsole(console: Console) {
        consoleDao.insert(console)
    }

    suspend fun updateConsole(console: Console) {
        consoleDao.update(console)
    }

    suspend fun deleteConsole(console: Console) {
        if (console.imageName != null) {
            ImageStorageHelper.deleteImage(ImageStorageHelper.IMAGE_PATH, console.imageName.toString())
        }
        consoleDao.delete(console)
    }

    suspend fun deleteAllConsoles() {
        consoleDao.deleteAllConsoles()
    }

    fun getAllConsoles(): LiveData<List<Console>> {
        return allConsoles
    }
}