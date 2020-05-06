package com.matthew.carvalhodagenais.cubedcollector.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.cubedcollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.cubedcollector.data.dao.ConditionDao
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Condition
import kotlinx.coroutines.coroutineScope

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

    suspend fun getConditionByCodeAndType(code: String, typeID: Int): Condition = coroutineScope {
        conditionDao.getConditionByCodeAndTypeAsync(code, typeID)
    }

    fun getConditionById(id: Int): LiveData<Condition> {
        return conditionDao.getConditionById(id)
    }

}