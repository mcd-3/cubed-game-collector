package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import android.text.Editable
import android.text.TextWatcher
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel

class TextWatcherGenerator {
    fun generate(viewModel: AccessoryAddEditViewModel): TextWatcher {
        return object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.viewFormChanged = true
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        };
    }
}