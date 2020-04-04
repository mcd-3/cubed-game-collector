package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.viewactions.ImageButtonActions
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel

class GameDetailFragment: Fragment() {

    private lateinit var detailViewModel: GameDetailViewModel

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameDetailFragment"
        const val FROM_FAVOURITE_REQUEST: Int = 1
        fun newInstance() =
            GameDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailViewModel = (activity as MainActivity).getGameDetailViewModel()
        val binding = DataBindingUtil.inflate<FragmentGameDetailBinding>(
            inflater, R.layout.fragment_game_detail, container, false
        ).apply {
            this.lifecycleOwner = this@GameDetailFragment
            this.viewModel = detailViewModel
            this.imageButtonActions =
                ImageButtonActions()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_game_detail, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_edit -> {
            val req =
                if (GameDetailFragmentArgs.fromBundle(arguments!!).FROMFAVOURITE == FROM_FAVOURITE_REQUEST)
                    1
                else 2
            val action =
                GameDetailFragmentDirections.actionGameDetailFragmentToGameAddEditFragment(
                    GameAddEditFragment.EDIT_REQUEST, req
                )
            (activity as MainActivity).getGameAddEditViewModel().setSelectedGame(
                detailViewModel.getSelectedGame()!!
            )
            findNavController().navigate(action)
            true
        }
        R.id.menu_delete -> {
            showDeleteDialog()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Prompts the user for whether or not the game will be deleted
     */
    private fun showDeleteDialog() {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(getString(R.string.game_detail_delete_alert_title))
            .setMessage(getString(R.string.game_detail_delete_alert_body))
            .setPositiveButton(getString(R.string.game_detail_delete_alert_positive),
                alertPositiveOnClick)
            .setNegativeButton(getString(R.string.game_detail_delete_alert_negative)){
                    dialog, _ -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    /**
     * AlertDialog OnClickListener to delete the currently selected game
     */
    private val alertPositiveOnClick = DialogInterface.OnClickListener { _, _ ->
        detailViewModel.delete(detailViewModel.getSelectedGame()!!)
        findNavController().navigate(R.id.action_gameDetailFragment_to_gameListFragment)
    }
}