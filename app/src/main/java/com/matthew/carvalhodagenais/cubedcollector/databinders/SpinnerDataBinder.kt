package com.matthew.carvalhodagenais.cubedcollector.databinders

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Type
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.SpinnerOnItemSelectedListenerGenerator
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.AccessoryAddEditViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.ConsoleAddEditViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameAddEditViewModel

class SpinnerDataBinder {
    companion object {
        private const val INDEX_DISC = 1
        private const val INDEX_CONSOLE = 6
        private const val INDEX_ACCESSORY = 11

        @JvmStatic
        @BindingAdapter("bind:viewModel", "bind:lifecycleOwner", "bind:defaultSelection")
        fun setSpinnerRegionEntries(
            spinner: Spinner,
            vm: AndroidViewModel,
            lco: LifecycleOwner,
            defaultSelection: Int
        ) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            val repository: RegionRepository? = when {
                vm is GameAddEditViewModel -> vm.getRegionRepository()
                vm is ConsoleAddEditViewModel -> vm.getRegionRepository()
                else -> null
            }
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            repository?.getRegionCodes()?.observe(lco, Observer { items ->
                items.forEach {
                    list.add(it)
                }
                adapter.notifyDataSetChanged()
                spinner.adapter = adapter
                spinner.setSelection(defaultSelection - INDEX_DISC)
                val slg = SpinnerOnItemSelectedListenerGenerator()
                spinner.onItemSelectedListener = slg.generate(vm)
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
                    val slg = SpinnerOnItemSelectedListenerGenerator()
                    spinner.onItemSelectedListener = slg.generate(vm)
                })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }
}