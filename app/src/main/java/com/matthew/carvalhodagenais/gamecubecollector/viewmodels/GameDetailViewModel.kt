package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import kotlinx.coroutines.launch

class GameDetailViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var repository = GameRepository(application)
    private var regionRepo = RegionRepository(application)
    private var conditionRepo = ConditionRepository(application)

    fun update(game: Game) = viewModelScope.launch {
        repository.updateGame(game)
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

    fun getRegionRepository(): RegionRepository = regionRepo

    fun getConditionRepository(): ConditionRepository = conditionRepo

    private fun toggleFavourite() {
        selectedGame.value!!.isFavourite = !(selectedGame.value!!.isFavourite)!!
        update(selectedGame.value!!)
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
                Log.e("EXCEPTION", "View cannot be an ImageButton.\n${e.message}"),
                Toast.LENGTH_SHORT).show()
        }
    }
}