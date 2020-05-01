package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Type
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.gamecubecollector.ui.ConsoleAddEditFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ConsoleAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedConsole = MutableLiveData<Console>()
    private var repository = ConsoleRepository(application)
    private var regionRepo = RegionRepository(application)
    private var conditionRepo = ConditionRepository(application)

    var viewFormChanged: Boolean = false

    fun insert(console: Console) = viewModelScope.launch {
        repository.insertConsole(console)
        clearCurrentlySelectedConsole()
    }

    fun update(console: Console) = viewModelScope.launch {
        repository.updateConsole(console)
        clearCurrentlySelectedConsole()
    }

    fun setSelectedConsole(console: Console) {
        selectedConsole.value = console
    }

    fun getSelectedConsole(): Console? = selectedConsole.value

    fun getRegionRepository(): RegionRepository = regionRepo

    fun getConditionRepository(): ConditionRepository = conditionRepo

    fun getConditionTypeID(): Int = Type.CONSOLE_ID

    fun clearCurrentlySelectedConsole() {
        selectedConsole = MutableLiveData<Console>()
    }

    /**
     * Saves a console to the database
     */
    fun saveConsole(
        requestInt: Int,
        title: String,
        desc: String?,
        isModded: Boolean?,
        regionCode: String,
        conditionCode: String,
        bitmap: Bitmap
    ) = viewModelScope.launch {
        var regionId: Int? = null
        val getRegionIdOperation = async {
            val region = regionRepo.getRegionByCode(regionCode)
            regionId = region.id
        }
        getRegionIdOperation.await()

        var conditionId: Int? = null
        val getConditionIdOperation = async {
            val condition = conditionRepo.getConditionByCodeAndType(conditionCode, Type.CONSOLE_ID)
            conditionId = condition.id
        }
        getConditionIdOperation.await()

        val name = ImageStorageHelper.generateUniqueImageName()
        ImageStorageHelper.save(
            getApplication<Application>().applicationContext,
            bitmap,
            name
        )

        val console = Console(
            title = title,
            description = desc,
            isModded = isModded,
            regionId = regionId,
            conditionId = conditionId
        )
        console.imageName = name

        if (requestInt == ConsoleAddEditFragment.EDIT_REQUEST) { // Edit the console
            console.id = selectedConsole.value!!.id
            update(console)
        } else { // Add new console
            insert(console)
        }
    }

}