package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentConsoleDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleDetailViewModel

class ConsoleDetailFragment: Fragment() {

    private lateinit var detailViewModel: ConsoleDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        detailViewModel = (activity as MainActivity).getConsoleDetailViewModel()
        val binding = DataBindingUtil.inflate<FragmentConsoleDetailBinding>(
            inflater, R.layout.fragment_console_detail, container, false
        ).apply {
            this.lifecycleOwner = this@ConsoleDetailFragment
            this.viewModel = detailViewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_item_detail, menu)
        super.onPrepareOptionsMenu(menu)
    }

}