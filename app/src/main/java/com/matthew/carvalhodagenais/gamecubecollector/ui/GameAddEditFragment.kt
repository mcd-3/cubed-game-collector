package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
import java.util.*

class GameAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: GameAddEditViewModel
    private var isFav: Boolean = false

    private var releaseDateToSave: Calendar? = null
    private var buyDateToSave: Calendar? = null

    companion object {
        private const val REQUEST_CODE: String = "GameAddEditFragment.REQUEST_CODE"
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameAddEditFragment"
        const val ADD_REQUEST: Int = 1
        const val EDIT_REQUEST: Int = 2

        fun newInstance(request: Int): GameAddEditFragment {
            val fragment =
                GameAddEditFragment()
            val args = Bundle()
            args.putInt(REQUEST_CODE, request)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEditViewModel = (activity as MainActivity).getGameAddEditViewModel()
        val binding = DataBindingUtil.inflate<FragmentGameAddEditBinding>(
            inflater, R.layout.fragment_game_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@GameAddEditFragment
            this.viewModel = addEditViewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set OnClickListeners for the "pick date" buttons
        release_date_calendar_image_button.setOnClickListener(releaseDateOnClickListener)
        release_date_clear_image_button.setOnClickListener(releaseDateClearOnClick)
        buy_date_calendar_image_button.setOnClickListener(buyDateOnClickListener)
        buy_date_clear_image_button.setOnClickListener(buyDateClearOnClick)

        // Set all values in the UI
        if (arguments!!.getInt(REQUEST_CODE) == EDIT_REQUEST) {
            setUIValues(addEditViewModel.getSelectedGame()!!)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_add_edit, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_save -> {
            if (title_edit_text.text?.trim().toString().isEmpty()) {
                Toast.makeText(context,
                    getString(R.string.toast_no_title_warning),
                    Toast.LENGTH_SHORT).show()
            } else {
                saveGame()
                val transaction =
                    activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(this@GameAddEditFragment.id,
                    GameListFragment.newInstance(),
                    GameListFragment.FRAGMENT_TAG
                )
                transaction.commit()
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets all UI values from game data
     *
     * @param game Game
     */
    private fun setUIValues(game: Game) {
        // Set the values
//        setFavouriteStarDrawable(game.isFavourite ?: false)
        release_date_edit_text.setText(createDateString(game.releaseDate))
        buy_date_edit_text.setText(createDateString(game.boughtDate))

        // Set the dates to save and "clear date" buttons clickable state
        val cal: Calendar = Calendar.getInstance()
        if (game.releaseDate != null) {
            cal.time = game.releaseDate
            releaseDateToSave = cal
            release_date_clear_image_button.isClickable = true
        } else {
            release_date_clear_image_button.isClickable = false
        }
        if (game.boughtDate != null) {
            cal.time = game.boughtDate
            buyDateToSave = cal
            buy_date_clear_image_button.isClickable = true
        } else {
            buy_date_clear_image_button.isClickable = false
        }
    }

    /**
     * Saves the game to the database by grabbing all values from the inputs
     */
    private fun saveGame() {
//        val g: Game = Game(title_edit_text.text.toString()).apply {
//            developers = setNullIfEmptyString(developer_edit_text.text.toString())
//            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
//            releaseDate = null
//            regionId = null//condition_edit_text.text.toString().toInt()
//            boughtDate = null
//            condition = null//condition_edit_text.text.toString().toInt()
//            pricePaid = null//price_paid_edit_text.text.toString().toDouble()
//            hasCase = true
//            hasManual = true
//        }
//        g.isFavourite = false
//        g.imagePath = ""
//        addEditViewModel.insert(g)
        val game: Game = Game(title_edit_text.text.toString()).apply {
            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
            developers = setNullIfEmptyString(developer_edit_text.text.toString())
            releaseDate = releaseDateToSave?.time
            pricePaid = setNullIfEmptyString(price_paid_edit_text.text.toString())?.toDouble()
            boughtDate = buyDateToSave?.time
            hasCase = case_checkbox.isChecked
            hasManual = manual_checkbox.isChecked
        }
        game.isFavourite = isFav

        if (arguments!!.getInt(REQUEST_CODE) == EDIT_REQUEST) {
            game.id = addEditViewModel.getSelectedGame()!!.id
            addEditViewModel.update(game)
        } else {
            addEditViewModel.insert(game)
        }

        addEditViewModel.clearCurrentlySelectedGame()
    }

    /**
     * Creates a formatted date string from a Date
     *
     * @param date Date
     * @return String
     */
    private fun createDateString(date: Date?): String {
        val str: String
        if (date != null) {
            val cal = Calendar.getInstance()
            cal.time = Date(date.time)
            str = "${cal.get(Calendar.DAY_OF_MONTH)}/" +
                    "${cal.get(Calendar.MONTH)}/" +
                    "${cal.get(Calendar.YEAR)}"
        } else {
            str = getString(R.string.date_default)
        }
        return str
    }

    /**
     * Sets a String to null if it is empty
     */
    private fun setNullIfEmptyString(string: String): String? =
        if (string.trim().isEmpty()) null else string

    /**
     * Changes the star drawable to filled or bordered
     *
     * @param isFav bool
     */
    private fun setFavouriteStarDrawable(isFav: Boolean) {
        if (isFav) {
            favourite_image_button.setImageDrawable(
                resources.getDrawable(R.drawable.ic_star_yellow_48dp, null))
        } else {
            favourite_image_button.setImageDrawable(
                resources.getDrawable(R.drawable.ic_star_border_yellow_48dp, null))
        }
    }

    /**
     * Creates a Calendar using a year, month, and day
     *
     * @param year int
     * @param month int
     * @param day int
     * @return Calendar
     */
    private fun createCalendar(year: Int, month: Int, day: Int): Calendar {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        return cal
    }

    /**
     * OnClickListener for the favourite button
     * Sets the boolean for "favourited" and changes the star drawable
     */
    private val favouriteButtonOnClick = View.OnClickListener {
        isFav = !isFav
        setFavouriteStarDrawable(isFav)
    }

    /**
     * OnClickListener to set a date
     * Sets the release date on click
     */
    private val releaseDateOnClickListener = View.OnClickListener {
        val cal = Calendar.getInstance()
        val datePicker = DatePickerDialog(context!!,
            DatePickerDialog.OnDateSetListener{_, mYear, mMonth, mDay ->
                release_date_edit_text.setText("${mDay}/${mMonth + 1}/${mYear}")
                releaseDateToSave = createCalendar(mYear, mMonth + 1, mDay)
                release_date_clear_image_button.isClickable = true
            }, cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    /**
     * OnClickListener to set a date
     * Sets the buy date on click
     */
    private val buyDateOnClickListener = View.OnClickListener {
        val cal = Calendar.getInstance()
        val datePicker = DatePickerDialog(context!!,
            DatePickerDialog.OnDateSetListener{_, mYear, mMonth, mDay ->
                buy_date_edit_text.setText("${mDay}/${mMonth + 1}/${mYear}")
                buyDateToSave = createCalendar(mYear, mMonth + 1, mDay)
                buy_date_clear_image_button.isClickable = true
            }, cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    /**
     * OnClickListener to clear a date
     * Clears the release date on click
     */
    private val releaseDateClearOnClick = View.OnClickListener {
        releaseDateToSave = null
        release_date_edit_text.setText(getString(R.string.date_default))
        it.isClickable = false
    }

    /**
     * OnClickListener to clear a date
     * Clears the bought date on click
     */
    private val buyDateClearOnClick = View.OnClickListener {
        buyDateToSave = null
        buy_date_edit_text.setText(getString(R.string.date_default))
        it.isClickable = false
    }
}