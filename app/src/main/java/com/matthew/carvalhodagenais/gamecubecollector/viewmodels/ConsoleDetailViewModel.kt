package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import kotlinx.coroutines.launch

class ConsoleDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedConsole = MutableLiveData<Console>()
    private var repository = ConsoleRepository(application)

    fun delete(console: Console) = viewModelScope.launch {
        repository.deleteConsole(console)
    }

    fun setSelectedConsole(console: Console) {
        selectedConsole.value = console
    }

    fun getSelectedConsole(): Console? = selectedConsole.value
}