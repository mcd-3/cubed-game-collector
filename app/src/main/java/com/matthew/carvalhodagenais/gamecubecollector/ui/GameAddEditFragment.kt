package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
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
     * Saves the game to the database by grabbing all values from the inputs
     */
    private fun saveGame() {
//        val g: Game = Game(title_edit_text.text.toString()).apply {
//            developers = setNullIfEmptyString(developer_edit_text.text.toString())
//            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
//            releaseDate = null
//            regionId = null//condition_edit_text.text.toString().toInt()
//            boughtDate = null
//            conditionId = null//condition_edit_text.text.toString().toInt()
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

        if (arguments!!.getInt(REQUEST_CODE) == EDIT_REQUEST) {
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