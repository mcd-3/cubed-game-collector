package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConsoleRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper
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