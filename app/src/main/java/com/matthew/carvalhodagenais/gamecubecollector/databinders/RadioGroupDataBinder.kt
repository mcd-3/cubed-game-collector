package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.gamecubecollector.helpers.generators.RadioGroupCheckedChangedListenerGenerator

class RadioGroupDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:viewModel")
        fun bindRadioGroupListener(
            rg: RadioGroup,
            viewModel: AndroidViewModel
        ) {
            val generator = RadioGroupCheckedChangedListenerGenerator()
            rg.setOnCheckedChangeListener(generator.generate(viewModel))
        }
    }
}