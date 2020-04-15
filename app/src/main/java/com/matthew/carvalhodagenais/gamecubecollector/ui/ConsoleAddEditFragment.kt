package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.R

class ConsoleAddEditFragment: Fragment() {

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_console_add_edit, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_add_edit, menu)
        return super.onPrepareOptionsMenu(menu)
    }

}