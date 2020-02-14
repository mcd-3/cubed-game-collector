package com.matthew.carvalhodagenais.gamecubecollector.viewmodels

import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import kotlinx.coroutines.launch
import java.lang.ClassCastException
import java.util.*

class GameAddEditViewModel(application: Application): AndroidViewModel(application) {

    private var selectedGame = MutableLiveData<Game>()
    private var gameRepository = GameRepository(application)
    private var regionRepository = RegionRepository(application)

    fun insert(game: Game) = viewModelScope.launch {
        gameRepository.insertGame(game)
    }

    fun update(game: Game) = viewModelScope.launch {
        gameRepository.updateGame(game)
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

    fun getGameRegions(): List<String> {
        return regionRepository.getRegionCodes()
    }

    /**
     * Creates a string from a Date or returns the default "No Date Set" text if date is null
     *
     * @param date
     * @return String
     */
    fun getDateString(date: Date?): String =
        DateHelper.createDateString(date)
            ?: getApplication<Application>().getString(R.string.date_default)

    // TODO: Use the tag to determine whether or not we should save as favourite or not
    fun createFavouriteButtonTag(): String {
        return (
                if (selectedGame.value != null && selectedGame.value?.isFavourite!!)
                    getApplication<Application>().resources.getString(R.string.star_filled_tag)
                else
                    getApplication<Application>().resources.getString(R.string.star_border_tag))
    }

    /**
     * OnClickListener for the favourite button
     * Changes the game's "favourited" status and changes the star drawable
     *
     * @param view
     */
    fun favouriteButtonOnClick(view: View) {
        try {
            var drawable = view.context.getDrawable(R.drawable.ic_star_yellow_48dp)
            if (view.tag == view.context.getString(R.string.star_filled_tag)) {
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
                Log.e("EXCEPTION", "View cannot be an ImageButton.\n${e.message}"),
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * OnClickListener for the date picker buttons
     * Opens a DatePickerDialog, sets the date in a given EditText, and enables a given ImageButton
     * for clearing the date
     *
     * @param editText
     * @param clearButton
     * @param dateButton
     */
    fun createDateOnClick(editText: View, clearButton: View, dateButton: View) {
        val cal = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            dateButton.context,
            DatePickerDialog.OnDateSetListener {_, mYear, mMonth, mDay ->
                (editText as EditText).setText("${mDay}/${mMonth + 1}/${mYear}")
                (clearButton as ImageButton).isClickable = true
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    /**
     * OnClickListener for the clear date buttons
     * Clears the date set in a given EditText and disables the button when clicked
     *
     * @param editText
     * @param button
     */
    fun clearDateOnClick(editText: View, button: View) {
        try {
            (editText as EditText).setText(editText.context.getString(R.string.date_default))
            (button as ImageButton).isClickable = false
        } catch (e: ClassCastException) {
            Log.e("EXCEPTION", e.message.toString())
        }
    }
}