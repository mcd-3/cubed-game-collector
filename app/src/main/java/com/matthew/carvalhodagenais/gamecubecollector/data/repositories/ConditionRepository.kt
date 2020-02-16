package com.matthew.carvalhodagenais.gamecubecollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.ConditionDao
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Condition

class ConditionRepository(application: Application) {

    private var conditionDao: ConditionDao
    private var allConditions: LiveData<List<Condition>>

    init {
        val database: CollectorDatabase = CollectorDatabase.getInstance(
            application.applicationContext
        )!!
        conditionDao = database.conditionDao()
        allConditions = conditionDao.getAllConditions()
    }

    fun getAllConditions(): LiveData<List<Condition>> {
        return allConditions
    }

    fun getConditionCodes(typeID: Int): LiveData<List<String>> {
        return conditionDao.getConditionCodesByType(typeID)
    }

    fun getConditionsByCodeAndType(code: String, typeID: Int): LiveData<List<Condition>> {
        return conditionDao.getConditionsByCodeAndType(code, typeID)
    }

}