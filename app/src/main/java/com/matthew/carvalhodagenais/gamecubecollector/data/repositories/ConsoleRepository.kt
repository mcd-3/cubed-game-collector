package com.matthew.carvalhodagenais.gamecubecollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.ConsoleDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console

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
        consoleDao.delete(console)
    }

    suspend fun deleteAllConsoles() {
        consoleDao.deleteAllConsoles()
    }

    fun getAllConsoles(): LiveData<List<Console>> {
        return allConsoles
    }
}