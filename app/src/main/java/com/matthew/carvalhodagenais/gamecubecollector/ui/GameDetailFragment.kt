package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import kotlinx.android.synthetic.main.fragment_game_detail.*

class GameDetailFragment: Fragment() {

    private lateinit var detailViewModel: GameDetailViewModel

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameDetailFragment"
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
            this.viewmodel = detailViewModel
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //favourite_image_button.setOnClickListener(favouriteOnClick)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_detail, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_edit -> {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            (activity as MainActivity).getGameAddEditViewModel().setSelectedGame(
                detailViewModel.getSelectedGame()!!
            )
            transaction.replace(this@GameDetailFragment.id,
                GameAddEditFragment.newInstance(
                    GameAddEditFragment.EDIT_REQUEST),
                GameAddEditFragment.FRAGMENT_TAG)
            transaction.addToBackStack(null)
            transaction.commit()
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
        val builder = AlertDialog.Builder(context)
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
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(this@GameDetailFragment.id,
            GameListFragment.newInstance()
        )
        transaction.commit()
    }

//    /**
//     * OnClickListener for the star ImageButton to favourite the Game
//     */
//    private val favouriteOnClick = View.OnClickListener {
//        if (detailViewModel.getIsFavourite()) {
//            favourite_image_button
//                .setImageDrawable(
//                    resources.getDrawable(R.drawable.ic_star_border_yellow_48dp,
//                    null))
//        } else {
//            favourite_image_button
//                .setImageDrawable(
//                    resources.getDrawable(R.drawable.ic_star_yellow_48dp,
//                    null))
//            Toast.makeText(context, getString(R.string.toast_favourite), Toast.LENGTH_SHORT).show()
//        }
//        detailViewModel.toggleFavourite()
//    }
}