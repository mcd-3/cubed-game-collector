package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.coroutines.launch

class GameDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)

    companion object {
        @JvmStatic
        @BindingAdapter("favouriteStarDrawable")
        fun setFavouriteStarDrawable(imageButton: ImageButton, isFav: Boolean) {
            val drawable =
                if (isFav) R.drawable.ic_star_yellow_48dp
                else R.drawable.ic_star_border_yellow_48dp
            Glide.with(imageButton.context)
                .load(drawable)
                .into(imageButton)
        }
    }

    fun delete(game: Game) = viewModelScope.launch {
        repository.deleteGame(game)
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun getIsFavourite(): Boolean {
        return selectedGame.value!!.isFavourite!!
    }

    fun toggleFavourite() {
        selectedGame.value!!.isFavourite = !(selectedGame.value!!.isFavourite)!!
        update()
    }

    private fun update() = viewModelScope.launch {
        repository.updateGame(selectedGame.value!!)
    }

    /**
     * OnClickListener for the favourite button
     * Changes the game's "favourited" status and changes the star drawable
     *
     * @param view
     */
    fun favouriteButtonOnClick(view: View) {
        try {
            var drawable = R.drawable.ic_star_border_yellow_48dp
            if (!selectedGame.value!!.isFavourite!!) {
                drawable = R.drawable.ic_star_yellow_48dp
                Toast.makeText(
                    view.context,
                    view.context.getString(R.string.toast_favourite),
                    Toast.LENGTH_SHORT).show()
            }
            Glide.with(view.context)
                .load(drawable)
                .into(view as ImageButton)
            toggleFavourite()
        } catch (e: TypeCastException) {
            Toast.makeText(
                view.context,
                Log.e("EXCEPTION", "View cannot be an ImaneButton.\n${e.message}"),
                Toast.LENGTH_SHORT).show()
        }
    }
}