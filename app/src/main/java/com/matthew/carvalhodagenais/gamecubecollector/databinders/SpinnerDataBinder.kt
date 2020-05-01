package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Type
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel

class SpinnerDataBinder {
    companion object {
        private const val INDEX_DISC = 1
        private const val INDEX_CONSOLE = 6
        private const val INDEX_ACCESSORY = 11

        @JvmStatic
        @BindingAdapter("bind:repository", "bind:lifecycleOwner", "bind:defaultSelection")
        fun setSpinnerRegionEntries(
            spinner: Spinner,
            repo: RegionRepository,
            lco: LifecycleOwner,
            defaultSelection: Int
        ) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            repo.getRegionCodes().observe(lco, Observer { items ->
                items.forEach {
                    list.add(it)
                }
                adapter.notifyDataSetChanged()
                spinner.adapter = adapter
                spinner.setSelection(defaultSelection - INDEX_DISC)
            })
        }

        @JvmStatic
        @BindingAdapter("bind:viewModel", "bind:lifecycleOwner", "bind:defaultSelection", "bind:typeID")
        fun setSpinnerConditionEntries(
            spinner: Spinner,
            vm: AndroidViewModel,
            lco: LifecycleOwner,
            defaultSelection: Int,
            type: Int
        ) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            val repository: ConditionRepository? = when {
                vm is GameAddEditViewModel -> vm.getConditionRepository()
                vm is ConsoleAddEditViewModel -> vm.getConditionRepository()
                vm is AccessoryAddEditViewModel -> vm.getConditionRepository()
                else -> null
            }
            if (repository != null) {
                repository.getConditionCodes(type).observe(lco, Observer { items ->
                    items.forEach {
                        list.add(it)
                    }
                    adapter.notifyDataSetChanged()
                    val index = when(type) {
                        Type.CD_ID -> INDEX_DISC
                        Type.CONSOLE_ID -> INDEX_CONSOLE
                        Type.ACCESSORY_ID -> INDEX_ACCESSORY
                        else -> INDEX_DISC
                    }
                    spinner.setSelection(defaultSelection - index)
                    //TODO: Add spinner listener over here!
                })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }
}