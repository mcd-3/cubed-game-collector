package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

class SpinnerDataBinder {

    companion object {
        @JvmStatic
        @BindingAdapter("bind:entries")
        fun setSpinnerEntries(spinner: Spinner, strList: List<String>) {
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, strList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

}