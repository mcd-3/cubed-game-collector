package com.matthew.carvalhodagenais.cubedcollector.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.matthew.carvalhodagenais.cubedcollector.MainActivity
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.databinding.FragmentAccessoryDetailBinding
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.AccessoryDetailViewModel

class AccessoryDetailFragment: Fragment() {

    private lateinit var detailViewModel: AccessoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailViewModel = (activity as MainActivity).getAccessoryDetailViewModel()
        val binding = DataBindingUtil.inflate<FragmentAccessoryDetailBinding>(
            inflater, R.layout.fragment_accessory_detail, container, false
        ).apply {
            this.lifecycleOwner = this@AccessoryDetailFragment
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean  = when(item.itemId) {
        R.id.menu_delete -> {
            showDeleteDialog()
            true
        }
        R.id.menu_edit -> {
            val action = AccessoryDetailFragmentDirections.actionAccessoryDetailFragmentToAccessoryAddEditFragment(
                AccessoryAddEditFragment.EDIT_REQUEST, getString(R.string.navigation_accessory_edit_title)
            )
            (activity as MainActivity).getAccessoryAddEditViewModel().setSelectedAccessory(
                detailViewModel.getSelectedAccessory()
            )
            findNavController().navigate(action)
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    /**
     * Prompts the user for whether or not the game will be deleted
     */
    private fun showDeleteDialog() {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(getString(R.string.accessory_detail_delete_alert_title))
            .setMessage(getString(R.string.accessory_detail_delete_alert_body))
            .setPositiveButton(getString(R.string.delete_alert_positive),
                alertPositiveOnClick)
            .setNegativeButton(getString(R.string.delete_alert_negative)){
                    dialog, _ -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    /**
     * AlertDialog OnClickListener to delete the currently selected game
     */
    private val alertPositiveOnClick = DialogInterface.OnClickListener { _, _ ->
        detailViewModel.delete(detailViewModel.getSelectedAccessory())
        findNavController().navigate(R.id.action_accessoryDetailFragment_to_nav_accessories)
    }

}