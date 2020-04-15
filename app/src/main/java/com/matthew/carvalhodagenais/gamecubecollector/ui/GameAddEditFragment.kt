package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.helpers.DateHelper
import com.matthew.carvalhodagenais.gamecubecollector.databinders.viewactions.ImageButtonActions
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.favourite_image_button

class GameAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: GameAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "GameAddEditFragment.REQUEST_CODE"
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameAddEditFragment"
        const val ADD_REQUEST: Int = 1
        const val EDIT_REQUEST: Int = 2
        const val FROM_FAVOURITE_REQUEST: Int = 1

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
            this.imageButtonActions = ImageButtonActions()
            this.navController = findNavController()
        }
        binding.setReleaseDateEditText(binding.releaseDateEditText)
        binding.setBuyDateEditText(binding.buyDateEditText)
        binding.setReleaseDateClearImageButton(binding.releaseDateClearImageButton)
        binding.setBuyDateClearImageButton(binding.buyDateClearImageButton)
        setHasOptionsMenu(true)

        // Set the title
        if (GameAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST == EDIT_REQUEST) {
            activity?.toolbar?.title = getString(R.string.navigation_game_edit_title)
        } else {
            activity?.toolbar?.title = getString(R.string.navigation_game_add_title)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_add_edit, menu)
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
                    condition_spinner.selectedItem.toString().trim(),
                    cover_art_image_view.drawable.toBitmap()
                )
                if (GameAddEditFragmentArgs.fromBundle(arguments!!).FROMFAVOURITE == FROM_FAVOURITE_REQUEST) {
                    findNavController().navigate(R.id.action_gameAddEditFragment_to_favouriteGameListFragment)
                } else {
                    findNavController().navigate(R.id.action_gameAddEditFragment_to_gameListFragment)
                }
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}