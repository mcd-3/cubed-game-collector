package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository

class ConsoleListViewModel(application: Application): AndroidViewModel(application) {

    private var repository = ConsoleRepository(application)

    fun getAllConsoles(): LiveData<List<Console>>
            = repository.getAllConsoles()
}