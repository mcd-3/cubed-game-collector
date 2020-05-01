package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Type
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.AccessoryRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.gamecubecollector.ui.AccessoryAddEditFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AccessoryAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedAccessory = MutableLiveData<Accessory>()
    private var repository = AccessoryRepository(application)
    private var conditionRepo = ConditionRepository(application)

    var viewFormChanged: Boolean = false

    fun insert(accessory: Accessory) = viewModelScope.launch {
        repository.insertAccessory(accessory)
        clearCurrentlySelectedAccessory()
    }

    fun update(accessory: Accessory) = viewModelScope.launch {
        repository.updateAccessory(accessory)
        clearCurrentlySelectedAccessory()
    }

    fun clearCurrentlySelectedAccessory() {
        selectedAccessory = MutableLiveData<Accessory>()
    }

    fun setSelectedAccessory(accessory: Accessory) {
        selectedAccessory.value = accessory
    }

    fun getSelectedAccessory(): Accessory? = selectedAccessory.value

    fun getConditionRepository(): ConditionRepository = conditionRepo

    fun getConditionTypeID(): Int = Type.ACCESSORY_ID

    /**
     * Saves an accessory to the database
     */
    fun saveAccessory(
        requestInt: Int,
        title: String,
        desc: String?,
        conditionCode: String,
        bitmap: Bitmap
    ) = viewModelScope.launch {
        var conditionId: Int? = null
        val getConditionIdOperation = async {
            val condition = conditionRepo.getConditionByCodeAndType(conditionCode, Type.ACCESSORY_ID)
            conditionId = condition.id
        }
        getConditionIdOperation.await()

        val name = ImageStorageHelper.generateUniqueImageName()
        ImageStorageHelper.save(
            getApplication<Application>().applicationContext,
            bitmap,
            name
        )

        val acc = Accessory(
            title = title,
            description = desc,
            conditionId = conditionId
        )
        acc.imageName = name

        if (requestInt == AccessoryAddEditFragment.EDIT_REQUEST) { // Edit the console
            acc.id = selectedAccessory.value!!.id
            update(acc)
        } else { // Add new console
            insert(acc)
        }
    }

}