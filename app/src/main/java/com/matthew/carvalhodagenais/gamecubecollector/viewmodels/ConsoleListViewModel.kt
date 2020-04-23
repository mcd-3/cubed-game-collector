package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import kotlinx.coroutines.launch

class ConsoleListViewModel(application: Application): AndroidViewModel(application) {

    private var repository = ConsoleRepository(application)

    fun delete(console: Console) = viewModelScope.launch {
        repository.deleteConsole(console)
    }

    fun getAllConsoles(): LiveData<List<Console>>
            = repository.getAllConsoles()
}