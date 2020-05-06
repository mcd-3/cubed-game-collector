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
import com.matthew.carvalhodagenais.cubedcollector.databinding.FragmentConsoleAddEditBinding
import com.matthew.carvalhodagenais.cubedcollector.helpers.ImageStorageHelper
import com.matthew.carvalhodagenais.cubedcollector.helpers.generators.TextWatcherGenerator
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.ConsoleAddEditViewModel
import kotlinx.android.synthetic.main.fragment_console_add_edit.*

class ConsoleAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: ConsoleAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "ConsoleAddEditFragment.REQUEST_CODE"
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
        private const val TITLE_KEY = "TITLE"
        private const val DESCRIPTION_KEY = "DESC"
        private const val CONDITION_KEY = "COND"
        private const val REGION_KEY = "REG"
        private const val MODDED_KEY = "MOD"
        private const val IMAGE_NAME = "TEMP_CON_IMG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEditViewModel = (activity as MainActivity).getConsoleAddEditViewModel()
        val tw = TextWatcherGenerator()
        val binding = DataBindingUtil.inflate<FragmentConsoleAddEditBinding>(
            inflater, R.layout.fragment_console_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@ConsoleAddEditFragment
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
        outState.putInt(REGION_KEY, region_spinner.selectedItemPosition)
        outState.putBoolean(MODDED_KEY, modded_yes.isChecked)
        ImageStorageHelper.saveNoAsync(context!!, console_image_view.drawable.toBitmap(), IMAGE_NAME)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            title_edit_text.setText(savedInstanceState.getString(TITLE_KEY))
            description_edit_text.setText(savedInstanceState.getString(DESCRIPTION_KEY))
            condition_spinner.setSelection(savedInstanceState.getInt(CONDITION_KEY))
            region_spinner.setSelection(savedInstanceState.getInt(REGION_KEY))
            if (savedInstanceState.getBoolean(MODDED_KEY))
                modded_yes.isChecked = true
            else
                modded_no.isChecked = true
            Glide.with(context!!)
                .load(ImageStorageHelper.getBitmap(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)!!)
                .into(console_image_view)
            ImageStorageHelper.deleteImageNoAsync(ImageStorageHelper.IMAGE_PATH, IMAGE_NAME)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_save -> {
            if (title_edit_text.text.toString().trim() != "") { // Save the console
                (activity as MainActivity).getConsoleAddEditViewModel().saveConsole(
                    ConsoleAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST,
                    title_edit_text.text.toString(),
                    description_edit_text.text.toString().trim(),
                    modded_yes.isChecked,
                    region_spinner.selectedItem.toString().trim(),
                    condition_spinner.selectedItem.toString().trim(),
                    console_image_view.drawable.toBitmap()
                )
                findNavController().navigate(R.id.action_consoleAddEditFragment_to_nav_consoles)
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