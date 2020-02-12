package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.coroutines.launch

class GameAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)

    fun insert(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun update(game: Game) = viewModelScope.launch {
        repository.updateGame(game)
    }

    fun setSelectedGame(game: Game) {
        selectedGame.value = game
    }

    fun getSelectedGame(): Game? {
        return selectedGame.value
    }

    fun clearCurrentlySelectedGame() {
        selectedGame = MutableLiveData<Game>()
    }

    /**
     * OnClickListener for the favourite button
     * Changes the game's "favourited" status and changes the star drawable
     *
     * @param view
     */
    fun favouriteButtonOnClick(view: View) {
        try {
            var drawable = view.context.getDrawable(R.drawable.ic_star_border_yellow_48dp)
            if (view.tag != view.context.getString(R.string.star_filled_tag)) {
                drawable = view.context.getDrawable(R.drawable.ic_star_border_yellow_48dp)
                view.tag = view.context.getString(R.string.star_border_tag)
            } else {
                view.tag = view.context.getString(R.string.star_filled_tag)
            }
            Glide.with(view.context)
                .load(drawable)
                .into(view as ImageButton)
        } catch (e: TypeCastException) {
            Toast.makeText(
                view.context,
                Log.e("EXCEPTION", "View cannot be an ImaneButton.\n${e.message}"),
                Toast.LENGTH_SHORT).show()
        }
    }

    // TODO: Use the tag to determine whether or not we should save as favourite or not
    fun createFavouriteButtonTag(): String {
        return (
            if (selectedGame.value != null && selectedGame.value?.isFavourite!!)
                getApplication<Application>().resources.getString(R.string.star_filled_tag)
            else
                getApplication<Application>().resources.getString(R.string.star_border_tag))
    }

}