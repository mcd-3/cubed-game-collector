package com.matthew.carvalhodagenais.gamecubecollector.databinders.viewactions

import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import java.util.*

/**
 * Class that contains OnClick methods for ImageButtons
 */
class ImageButtonActions {

    /**
     * Changes the icon depending on if the item is a favourite and updates the database
     *
     * @param button ImageButton
     * @param isFav Boolean
     * @param viewModel GameDetailViewModel
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
                "An error has occurred",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Changes the icon and tag of a favourite ImageButton
     *
     * @param button View
     */
    fun changeFavouriteImage(button: View) {
        try {
            var drawable = button.context.getDrawable(R.drawable.ic_star_yellow_48dp)
            if (button.tag == button.context.getString(R.string.star_filled_tag)) {
                drawable = button.context.getDrawable(R.drawable.ic_star_border_yellow_48dp)
                button.tag = button.context.getString(R.string.star_border_tag)
            } else {
                button.tag = button.context.getString(R.string.star_filled_tag)
            }
            Glide.with(button.context)
                .load(drawable)
                .into(button as ImageButton)
        } catch (e: TypeCastException) {
            Toast.makeText(
                button.context,
                "An error has occurred",
                Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Prompts the user for a date, and updates a given EditText when a date is selected.
     * Also disables a "clear date" button
     */
    fun pickDate(button: View, editText: View, clearButton: View) {
        val cal = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            button.context,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
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
     * Clears a given EditText and disables the button
     *
     * @param button View
     * @param editText EditText
     */
    fun clearEditText(button: View, editText: EditText) {
        try {
            editText.setText(editText.context.getString(R.string.date_default))
            (button as ImageButton).isClickable = false
        } catch (e: ClassCastException) {
            Toast.makeText(
                button.context,
                "An error has occurred",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}