package com.matthew.carvalhodagenais.cubedcollector.ui

import android.os.Bundle
import android.view.*
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.matthew.carvalhodagenais.cubedcollector.MainActivity
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.databinding.FragmentAccessoryAddEditBinding
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.TextWatcherGenerator
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.AccessoryAddEditViewModel
import kotlinx.android.synthetic.main.fragment_accessory_add_edit.*

class AccessoryAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: AccessoryAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "AccessoryAddEditFragment.REQUEST_CODE"
        private const val TITLE_KEY = "TITLE"
        private const val DESCRIPTION_KEY = "DESC"
        private const val CONDITION_KEY = "COND"
        private const val IMAGE_NAME = "TEMP_ACC_IMG"
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEditViewModel = (activity as MainActivity).getAccessoryAddEditViewModel()
        val tw = TextWatcherGenerator()
        val binding = DataBindingUtil.inflate<FragmentAccessoryAddEditBinding>(
            inflater, R.layout.fragment_accessory_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@AccessoryAddEditFragment
            this.viewModel = addEditViewModel
            this.navController = findNavController()
            this.textWatcher = tw.generate(addEditViewModel)
        }
        setHasOptionsMenu(true)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_add_edit, menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(TITLE_KEY, title_edit_text.text.toString())
        outState.putString(DESCRIPTION_KEY, description_edit_text.text.toString())
        outState.putInt(CONDITION_KEY, condition_spinner.selectedItemPosition)
        ImageStorageHelper.saveNoAsync(context!!, accessory_image_view.drawable.toBitmap(), IMAGE_NAME)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            title_edit_text.setText(savedInstanceState.getString(TITLE_KEY))
            description_edit_text.setText(savedInstanceState.getString(DESCRIPTION_KEY))
            condition_spinner.setSelection(savedInstanceState.getInt(CONDITION_KEY))
            Glide.with(context!!)
                .load(ImageStorageHelper.getBitmap(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)!!)
                .into(accessory_image_view)
            ImageStorageHelper.deleteImageNoAsync(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_save -> {
            if (title_edit_text.text.toString().trim() != "") { // Save the console
                (activity as MainActivity).getAccessoryAddEditViewModel().saveAccessory(
                    ConsoleAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST,
                    title_edit_text.text.toString().trim(),
                    description_edit_text.text.toString().trim(),
                    condition_spinner.selectedItem.toString().trim(),
                    accessory_image_view.drawable.toBitmap()
                )
                findNavController().navigate(R.id.action_accessoryAddEditFragment_to_nav_accessories)
            } else { // Warn the user about needing a title
                Snackbar.make(
                    view!!,
                    getString(R.string.toast_no_title_warning),
                    Snackbar.LENGTH_SHORT)
                    .show()
            }
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

}