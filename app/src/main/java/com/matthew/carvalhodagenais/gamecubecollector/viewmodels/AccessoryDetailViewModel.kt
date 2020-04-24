package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.AccessoryRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import kotlinx.coroutines.launch

class AccessoryDetailViewModel(application: Application): AndroidViewModel(application) {
    private var selectedAccessory = MutableLiveData<Accessory>()
    private var repository = AccessoryRepository(application)
    private var conditionRepo: ConditionRepository = ConditionRepository(application)

    fun delete(accessory: Accessory) = viewModelScope.launch {
        repository.deleteAccessory(accessory)
    }

    fun setSelectedAccessory(accessory: Accessory) {
        selectedAccessory.value = accessory
    }

    fun getSelectedAccessory(): Accessory = selectedAccessory.value!!

    fun getConditionRepository(): ConditionRepository = conditionRepo
}