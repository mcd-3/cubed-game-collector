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
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentAccessoryAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_accessory_add_edit.*

class AccessoryAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: AccessoryAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "AccessoryAddEditFragment.REQUEST_CODE"
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEditViewModel = (activity as MainActivity).getAccessoryAddEditViewModel()
        val binding = DataBindingUtil.inflate<FragmentAccessoryAddEditBinding>(
            inflater, R.layout.fragment_accessory_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@AccessoryAddEditFragment
            this.viewModel = addEditViewModel
            this.navController = findNavController()
        }
        setHasOptionsMenu(true)

        // Set the title
        if (AccessoryAddEditFragmentArgs.fromBundle(arguments!!).ADDEDITREQUEST == EDIT_REQUEST) {
            activity?.toolbar?.title = getString(R.string.navigation_accessory_edit_title)
        } else {
            activity?.toolbar?.title = getString(R.string.navigation_accessory_add_title)
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_add_edit, menu)
        super.onPrepareOptionsMenu(menu)
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