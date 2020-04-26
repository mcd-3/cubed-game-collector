package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.internal.ContextUtils
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import java.util.*

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
        @BindingAdapter("bind:lifecycleOwner", "bind:repository", "bind:conditionId")
        fun setConditionText(textView: TextView, lco: LifecycleOwner, repo: ConditionRepository, conditionId: Int?) {
            if (conditionId != null) {
                repo.getConditionById(conditionId).observe(lco, Observer {
                    textView.text = "${it.name} [${it.code}]"
                })
            } else {
                textView.text = textView.context.getString(R.string.no_condition)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:isModded")
        fun setIsModdedText(textView: TextView, isModded: Boolean) {
            if (isModded) {
                textView.text = textView.context.getString(R.string.yes)
            } else {
                textView.text = textView.context.getString(R.string.no)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:description")
        fun setDescription(textView: TextView, description: String?) {
            if (description != null && description.trim() != "") {
                textView.text = "\" $description \""
            } else {
                textView.text = textView.context.getString(R.string.no_description_available)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:date")
        fun setDateString(textView: TextView, date: Date?) {
            if (date == null) {
                textView.text = textView.context.getString(R.string.no_date)
            } else {
                textView.text = DateHelper.createDateString(date)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:hasCase", "bind:hasManual")
        fun setCompletenessText(textView: TextView, hasCase: Boolean, hasManual: Boolean) {
            var str = textView.context.getString(R.string.completeness_disc)
            if (hasCase && hasManual) {
                str = textView.context.getString(R.string.completeness_cib)
            } else if (hasCase) {
                str = textView.context.getString(R.string.completeness_case)
            } else if (hasManual) {
                str = textView.context.getString(R.string.completeness_manual)
            }
            textView.text = str
        }

        @JvmStatic
        @BindingAdapter("bind:isDefault")
        fun setDefaultTextStyle(textView: TextView, isDefault: Boolean) {
            if (isDefault) {
                textView.setTypeface(null, Typeface.ITALIC)

                // get theme subtitle color
//                val typedValue = TypedValue()
//                val theme: Resources.Theme = textView.context.applicationContext.theme
//                theme.resolveAttribute(R.attr.subtitleTextColor, typedValue, true)
//                textView.setTextColor(typedValue.data)
            }
        }
    }
}