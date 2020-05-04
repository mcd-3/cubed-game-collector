package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import kotlinx.coroutines.launch

class ConsoleDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedConsole = MutableLiveData<Console>()
    private var repository = ConsoleRepository(application)
    private var regionRepo: RegionRepository = RegionRepository(application)
    private var conditionRepo: ConditionRepository = ConditionRepository(application)

    fun delete(console: Console) = viewModelScope.launch {
        repository.deleteConsole(console)
    }

    fun setSelectedConsole(console: Console) {
        selectedConsole.value = console
    }

    fun getSelectedConsole(): Console? = selectedConsole.value

    fun getConditionRepository(): ConditionRepository = conditionRepo

    fun getRegionRepository(): RegionRepository = regionRepo
}