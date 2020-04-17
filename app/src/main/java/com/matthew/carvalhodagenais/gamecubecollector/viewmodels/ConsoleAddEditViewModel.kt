package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import kotlinx.coroutines.launch

class ConsoleAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedConsole = MutableLiveData<Console>()
    private var repository = ConsoleRepository(application)

    fun setSelectedConsole(console: Console) {
        selectedConsole.value = console
    }

    fun getSelectedConsole(): Console? = selectedConsole.value

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