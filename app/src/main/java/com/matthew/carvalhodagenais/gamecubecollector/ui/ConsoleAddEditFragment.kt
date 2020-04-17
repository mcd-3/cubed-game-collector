package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentConsoleAddEditBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentConsoleDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleAddEditViewModel

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
        val binding = DataBindingUtil.inflate<FragmentConsoleAddEditBinding>(
            inflater, R.layout.fragment_console_add_edit, container, false
        ).apply {
            this.lifecycleOwner = this@ConsoleAddEditFragment
            this.viewModel = addEditViewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_add_edit, menu)
        return super.onPrepareOptionsMenu(menu)
    }

}