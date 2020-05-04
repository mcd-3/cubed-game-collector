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
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentConsoleAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentConsoleDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.helpers.generators.TextWatcherGenerator
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleAddEditViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_console_add_edit.*

class ConsoleAddEditFragment: Fragment() {

    private lateinit var addEditViewModel: ConsoleAddEditViewModel

    companion object {
        private const val REQUEST_CODE: String = "ConsoleAddEditFragment.REQUEST_CODE"
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
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