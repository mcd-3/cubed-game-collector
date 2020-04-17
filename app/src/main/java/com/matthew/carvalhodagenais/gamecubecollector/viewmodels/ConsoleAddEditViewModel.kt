package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import kotlinx.coroutines.launch

class ConsoleAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedConsole = MutableLiveData<Console>()
    private var repository = ConsoleRepository(application)
    private var regionRepo = RegionRepository(application)

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

    private fun clearCurrentlySelectedConsole() {
        selectedConsole = MutableLiveData<Console>()
    }

    /**
     * TODO: Finish this function and change params
     * Saves a console to the database
     */
    fun saveConsole(
        title: String,
        desc: String?
    ) = viewModelScope.launch {

    }

}