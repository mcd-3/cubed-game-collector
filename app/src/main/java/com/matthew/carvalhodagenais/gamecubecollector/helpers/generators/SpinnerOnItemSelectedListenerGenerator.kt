package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel

class SpinnerOnItemSelectedListenerGenerator {

    private var initialSelected: Boolean = false

    fun generate(vm: AndroidViewModel): AdapterView.OnItemSelectedListener {
        return object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (initialSelected) {
                    when {
                        vm is GameAddEditViewModel -> vm.viewFormChanged = true
                        vm is ConsoleAddEditViewModel -> vm.viewFormChanged = true
                        vm is AccessoryAddEditViewModel -> vm.viewFormChanged = true
                    }
                } else {
                    initialSelected = true
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}