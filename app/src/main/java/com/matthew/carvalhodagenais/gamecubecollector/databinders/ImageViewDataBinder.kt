package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper

class ImageViewDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:game")
        fun setFavouriteStarDrawable(imageView: ImageView, game: Game?) {
            if (game?.imageName != "" && game?.imageName != null) {
                Glide.with(imageView.context)
                    .load(ImageStorageHelper.getImageWithPath(game.imageName.toString()))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(imageView.context.getDrawable(R.drawable.no_art))
                    .into(imageView)
            }
        }
    }
}