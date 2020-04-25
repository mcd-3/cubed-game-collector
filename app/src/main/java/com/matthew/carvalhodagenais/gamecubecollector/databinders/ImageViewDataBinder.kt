package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ImageStorageHelper

class ImageViewDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:game")
        fun setGameCoverArt(imageView: ImageView, game: Game?) {
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

        @JvmStatic
        @BindingAdapter("bind:console")
        fun setConsoleImage(imageView: ImageView, console: Console?) {
            if (console?.imageName != null && console.imageName != "") {
                Glide.with(imageView.context)
                    .load(ImageStorageHelper.getImageWithPath(console.imageName.toString()))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(imageView.context.getDrawable(R.drawable.no_console))
                    .into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:accessory")
        fun setAccessoryImage(imageView: ImageView, accessory: Accessory?) {
            if (accessory?.imageName != null && accessory.imageName != "") {
                Glide.with(imageView.context)
                    .load(ImageStorageHelper.getImageWithPath(accessory.imageName.toString()))
                    .into(imageView)
            } else {
                Glide.with(imageView.context)
                    .load(imageView.context.getDrawable(R.drawable.no_accessory))
                    .into(imageView)
            }
        }

    }
}