package com.matthew.carvalhodagenais.cubedcollector.ui

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.matthew.carvalhodagenais.cubedcollector.MainActivity
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.databinding.FragmentGameAddEditBinding
import com.matthew.carvalhodagenais.cubedcollector.helpers.DateHelper
import com.matthew.carvalhodagenais.cubedcollector.databinders.viewactions.ImageButtonActions
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.TextWatcherGenerator
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameAddEditViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.favourite_image_button

class GameAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: GameAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "GameAddEditFragment.REQUEST_CODE"
        private const val TITLE_KEY = "TITLE"
        private const val DEVELOPER_KEY = "DEV"
        private const val PUBLISHER_KEY = "PUB"
        private const val REGION_KEY = "REG"
        private const val CONDITION_KEY = "COND"
        private const val RDATE_KEY = "RD"
        private const val BDATE_KEY = "BD"
        private const val PRICE_KEY = "PRICE"
        private const val CASE_KEY = "CASE"
        private const val MANUAL_KEY = "MANUAL"
        private const val IMAGE_NAME = "TEMP_GAME_IMG"
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.cubedcollector.ui.GameAddEditFragment"

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
        val tw = TextWatcherGenerator()
        val binding = DataBindingUtil.inflate<FragmentGameAddEditBinding>(
            inflater, R.layout.fragment_game_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@GameAddEditFragment
            this.viewModel = addEditViewModel
            this.imageButtonActions = ImageButtonActions()
            this.navController = findNavController()
            this.textWatcher = tw.generate(addEditViewModel)
            this.act = activity
        }
        binding.setReleaseDateEditText(binding.releaseDateEditText)
        binding.setBuyDateEditText(binding.buyDateEditText)
        binding.setReleaseDateClearImageButton(binding.releaseDateClearImageButton)
        binding.setBuyDateClearImageButton(binding.buyDateClearImageButton)
        setHasOptionsMenu(true)
        binding.executePendingBindings()
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

    override fun onSaveInstanceState(outState: Bundle) {
        if (isVisible) {
            outState.putString(TITLE_KEY, title_edit_text.text.toString())
            outState.putString(DEVELOPER_KEY, developer_edit_text.text.toString())
            outState.putString(PUBLISHER_KEY, publisher_edit_text.text.toString())
            outState.putInt(CONDITION_KEY, condition_spinner.selectedItemPosition)
            outState.putInt(REGION_KEY, region_spinner.selectedItemPosition)
            outState.putString(RDATE_KEY, release_date_edit_text.text.toString())
            outState.putString(BDATE_KEY, buy_date_edit_text.text.toString())
            outState.putString(PRICE_KEY, price_paid_edit_text.text.toString())
            outState.putBoolean(CASE_KEY, case_checkbox.isChecked)
            outState.putBoolean(MANUAL_KEY, manual_checkbox.isChecked)
            ImageStorageHelper.saveNoAsync(requireContext(), cover_art_image_view.drawable.toBitmap(), IMAGE_NAME)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            title_edit_text.setText(savedInstanceState.getString(TITLE_KEY))
            developer_edit_text.setText(savedInstanceState.getString(DEVELOPER_KEY))
            publisher_edit_text.setText(savedInstanceState.getString(PUBLISHER_KEY))
            condition_spinner.setSelection(savedInstanceState.getInt(CONDITION_KEY))
            region_spinner.setSelection(savedInstanceState.getInt(REGION_KEY))
            release_date_edit_text.setText(savedInstanceState.getString(RDATE_KEY))
            buy_date_edit_text.setText(savedInstanceState.getString(BDATE_KEY))
            setButtonColor()
            price_paid_edit_text.setText(savedInstanceState.getString(PRICE_KEY))
            case_checkbox.isChecked = savedInstanceState.getBoolean(CASE_KEY)
            manual_checkbox.isChecked = savedInstanceState.getBoolean(MANUAL_KEY)
            Glide.with(requireContext())
                .load(ImageStorageHelper.getBitmap(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)!!)
                .into(cover_art_image_view)
            ImageStorageHelper.deleteImageNoAsync(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_save -> {
            if (title_edit_text.text?.trim().toString().isEmpty()) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.toast_no_title_warning),
                    Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                addEditViewModel.saveGame(
                    GameAddEditFragmentArgs.fromBundle(requireArguments()).ADDEDITREQUEST,
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
                if (GameAddEditFragmentArgs.fromBundle(requireArguments()).FROMFAVOURITE == FROM_FAVOURITE_REQUEST) {
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

    /**
     * Sets the clear ImageButton colours and whether or not they are enabled
     * Used when the view is restored
     */
    private fun setButtonColor() {
        val typedValue = TypedValue()
        val theme: Resources.Theme = requireActivity().theme

        if (release_date_edit_text.text.toString() != getString(R.string.date_default)) {
            theme.resolveAttribute(android.R.attr.colorError, typedValue, true)
            release_date_clear_image_button.isEnabled = true
            release_date_clear_image_button.isClickable = true
        } else {
            theme.resolveAttribute(android.R.attr.colorButtonNormal, typedValue, true)
            release_date_clear_image_button.isEnabled = false
            release_date_clear_image_button.isClickable = false
        }
        release_date_clear_image_button.backgroundTintList = ColorStateList.valueOf(typedValue.data)

        if (buy_date_edit_text.text.toString() != getString(R.string.date_default)) {
            theme.resolveAttribute(android.R.attr.colorError, typedValue, true)
            buy_date_clear_image_button.isEnabled = true
            buy_date_clear_image_button.isClickable = true
        } else {
            theme.resolveAttribute(android.R.attr.colorButtonNormal, typedValue, true)
            buy_date_clear_image_button.isEnabled = false
            buy_date_clear_image_button.isClickable = false
        }
        buy_date_clear_image_button.backgroundTintList = ColorStateList.valueOf(typedValue.data)
    }

}