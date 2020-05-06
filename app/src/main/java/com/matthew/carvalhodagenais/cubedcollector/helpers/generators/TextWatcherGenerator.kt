package com.matthew.carvalhodagenais.cubedcollector.helpers.generators

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.AccessoryAddEditViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.ConsoleAddEditViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameAddEditViewModel

class TextWatcherGenerator {
    fun generate(viewModel: AndroidViewModel): TextWatcher {
        return object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                when {
                    viewModel is GameAddEditViewModel -> viewModel.viewFormChanged = true
                    viewModel is ConsoleAddEditViewModel -> viewModel.viewFormChanged = true
                    viewModel is AccessoryAddEditViewModel -> viewModel.viewFormChanged = true
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }
}