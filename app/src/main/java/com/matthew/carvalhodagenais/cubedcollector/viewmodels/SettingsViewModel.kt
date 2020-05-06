package com.matthew.carvalhodagenais.cubedcollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    val gameRepo: GameRepository = GameRepository(application)
    val consoleRepo: ConsoleRepository = ConsoleRepository(application)


    fun deleteAllData() = viewModelScope.launch {
        gameRepo.deleteAllGames()
        consoleRepo.deleteAllConsoles()
        ImageStorageHelper.deleteAllImages()
    }

}