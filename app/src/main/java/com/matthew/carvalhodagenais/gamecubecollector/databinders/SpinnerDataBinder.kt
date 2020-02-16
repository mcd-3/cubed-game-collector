package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository

class SpinnerDataBinder {

    companion object {
        @JvmStatic
        @BindingAdapter("bind:entries", "bind:lifecycleOwner")
        fun setSpinnerRegionEntries(spinner: Spinner, repo: RegionRepository, lco: LifecycleOwner) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            repo.getRegionCodes().observe(lco, Observer { items ->
                items.forEach {
                    list.add(it)
                }
                adapter.notifyDataSetChanged()
                spinner.setSelection(0)
            })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }

}