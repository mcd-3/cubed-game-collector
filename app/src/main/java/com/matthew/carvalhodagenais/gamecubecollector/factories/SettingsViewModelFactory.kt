package com.matthew.carvalhodagenais.gamecubecollector.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.SettingsViewModel

class SettingsViewModelFactory(private val application: Application):
        ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(application) as T
            else -> IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}") as T
        }
    }


}