package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.favourite_image_button
import kotlinx.android.synthetic.main.fragment_game_detail.*
import java.util.*

class GameAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: GameAddEditViewModel
    private var isFav: Boolean = false

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
        binding.setReleaseDateEditText(binding.releaseDateEditText)
        binding.setBuyDateEditText(binding.buyDateEditText)
        binding.setReleaseDateClearImageButton(binding.releaseDateClearImageButton)
        binding.setBuyDateClearImageButton(binding.buyDateClearImageButton)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_game_add_edit, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_save -> {
            if (title_edit_text.text?.trim().toString().isEmpty()) {
                Snackbar.make(
                    view!!,
                    getString(R.string.toast_no_title_warning),
                    Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                addEditViewModel.saveGame(
                    GameAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST,
                    title_edit_text.text.toString(),
                    publisher_edit_text.text.toString(),
                    developer_edit_text.text.toString(),
                    (if (release_date_edit_text.text.toString() != getString(R.string.date_default))
                        DateHelper.createDate(release_date_edit_text.text.toString())
                    else
                        null),
                    (if (price_paid_edit_text.text.toString().trim() != "")
                        price_paid_edit_text.text.toString().trim().toDouble()
                    else
                        null),
                    (if (buy_date_edit_text.text.toString() != getString(R.string.date_default))
                        DateHelper.createDate(buy_date_edit_text.text.toString())
                    else
                        null),
                    case_checkbox.isChecked,
                    manual_checkbox.isChecked,
                    favourite_image_button.tag.toString(),
                    region_spinner.selectedItem.toString().trim(),
                    condition_spinner.selectedItem.toString().trim()
                )
                findNavController().navigate(R.id.action_gameAddEditFragment_to_gameListFragment)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Saves the game to the database by grabbing all values from the inputs
     */
    private fun saveGame() {
        val game: Game = Game(title_edit_text.text.toString()).apply {
            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
            developers = setNullIfEmptyString(developer_edit_text.text.toString())
            releaseDate =
                if (release_date_edit_text.text.toString() != getString(R.string.date_default))
                    DateHelper.createDate(release_date_edit_text.text.toString())
                else
                    null
            pricePaid = setNullIfEmptyString(price_paid_edit_text.text.toString())?.toDouble()
            boughtDate =
                if (buy_date_edit_text.text.toString() != getString(R.string.date_default))
                    DateHelper.createDate(buy_date_edit_text.text.toString())
                else
                    null
            hasCase = case_checkbox.isChecked
            hasManual = manual_checkbox.isChecked
        }
        game.isFavourite = isFav

        if (GameAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST == EDIT_REQUEST) {
            game.id = addEditViewModel.getSelectedGame()!!.id
            addEditViewModel.update(game)
        } else {
            addEditViewModel.insert(game)
        }

        addEditViewModel.clearCurrentlySelectedGame()
    }

    /**
     * Sets a String to null if it is empty
     */
    private fun setNullIfEmptyString(string: String): String? =
        if (string.trim().isEmpty()) null else string

}