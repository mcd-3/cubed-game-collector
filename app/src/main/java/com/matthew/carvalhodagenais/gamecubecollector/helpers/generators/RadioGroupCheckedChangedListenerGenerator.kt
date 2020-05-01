package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel

class RadioGroupCheckedChangedListenerGenerator {

    private var initiallySelected: Boolean = false

    fun generate(vm: AndroidViewModel): RadioGroup.OnCheckedChangeListener {
        return object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (initiallySelected) {
                    when {
                        vm is GameAddEditViewModel -> vm.viewFormChanged = true
                        vm is ConsoleAddEditViewModel -> vm.viewFormChanged = true
                        vm is AccessoryAddEditViewModel -> vm.viewFormChanged = true
                    }
                }
                initiallySelected = true
            }
        }
    }
}