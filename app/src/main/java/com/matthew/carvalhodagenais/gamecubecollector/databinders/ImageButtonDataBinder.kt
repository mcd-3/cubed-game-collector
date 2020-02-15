package com.matthew.carvalhodagenais.gamecubecollector.databinders

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
    }
}