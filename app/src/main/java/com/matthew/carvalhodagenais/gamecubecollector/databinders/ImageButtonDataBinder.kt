package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.app.Activity
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R

class ImageButtonDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("favouriteStarDrawable")
        fun setFavouriteStarDrawable(imageButton: ImageButton, isFav: Boolean?) {
            val fav = isFav ?: false
            val drawable =
                if (fav) R.drawable.ic_star_yellow_48dp
                else R.drawable.ic_star_border_yellow_48dp
            Glide.with(imageButton.context)
                .load(drawable)
                .into(imageButton)
        }

        @JvmStatic
        @BindingAdapter("bind:parentActivity", "bind:isEnabled")
        fun setImageButtonColour(btn: ImageButton, activity: Activity, isEnabled: Boolean) {
            val typedValue = TypedValue()
            val theme: Resources.Theme = activity.theme
            if (isEnabled) {
                theme.resolveAttribute(android.R.attr.colorError, typedValue, true)
            } else {
                theme.resolveAttribute(android.R.attr.colorButtonNormal, typedValue, true)
            }
            btn.backgroundTintList = ColorStateList.valueOf(typedValue.data)
        }
    }
}