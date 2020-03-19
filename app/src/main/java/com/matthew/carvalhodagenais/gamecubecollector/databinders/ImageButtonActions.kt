package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel

/**
 * Class that contains OnClick methods for ImageButtons
 */
class ImageButtonActions {

    /**
     * Changes the icon depending on if the item is a favourite
     *
     * @param button ImageButton
     * @param isFav Boolean
     */
    fun changeFavouriteImage(button: View, isFavourite: Boolean, viewModel: GameDetailViewModel) {
        try {
            var drawable = R.drawable.ic_star_border_yellow_48dp
            if (!isFavourite) {
                drawable = R.drawable.ic_star_yellow_48dp
                Toast.makeText(
                    button.context,
                    button.context.getString(R.string.toast_favourite),
                    Toast.LENGTH_SHORT).show()
            }
            Glide.with(button.context)
                .load(drawable)
                .into(button as ImageButton)
            viewModel.toggleFavourite()
        } catch (e: TypeCastException) {
            Toast.makeText(
                button.context,
                "An error has occured",
                Toast.LENGTH_SHORT).show()
        }
    }
}