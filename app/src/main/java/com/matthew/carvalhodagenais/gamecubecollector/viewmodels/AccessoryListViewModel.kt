package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.AccessoryRepository
import kotlinx.coroutines.launch

class AccessoryListViewModel(application: Application): AndroidViewModel(application) {

    private var accessoryRepository = AccessoryRepository(application)

    fun delete(accessory: Accessory) = viewModelScope.launch {
        accessoryRepository.deleteAccessory(accessory)
    }

    fun getAllAccessories(): LiveData<List<Accessory>>
            = accessoryRepository.getAllAccessories()

}