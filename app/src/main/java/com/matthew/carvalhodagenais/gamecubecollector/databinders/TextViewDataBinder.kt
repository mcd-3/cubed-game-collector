package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository

class TextViewDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:lifecycleOwner", "bind:repository", "bind:regionId")
        fun setRegionText(textView: TextView, lco: LifecycleOwner, repo: RegionRepository, regionId: Int?) {
            if (regionId != null) {
                repo.getRegionById(regionId).observe(lco, Observer {
                    textView.text = "${it.name} [${it.code}]"
                })
            } else {
                textView.text = textView.context.getString(R.string.no_region)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:lifecycleOwner", "bind:repository", "bind:regionId")
        fun setConditionText(textView: TextView, lco: LifecycleOwner, repo: ConditionRepository, conditionId: Int?) {
            if (conditionId != null) {
                repo.getConditionById(conditionId).observe(lco, Observer {
                    textView.text = "${it.name} [${it.code}]"
                })
            } else {
                textView.text = textView.context.getString(R.string.no_condition)
            }
        }
    }
}