package com.matthew.carvalhodagenais.gamecubecollector

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_detail.*

class GameDetailFragment: Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var currentGame: Game

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.GameDetailFragment"
        fun newInstance() = GameDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_game_detail, container, false)
        viewModel = (activity as MainActivity).getGameViewModel()
        currentGame = viewModel.getSelectedGame()!!
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favourite_image_button.setOnClickListener(favouriteOnClick)
        initialDataSetup(currentGame)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_detail, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_edit -> {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(this@GameDetailFragment.id,
                GameAddEditFragment.newInstance(GameAddEditFragment.EDIT_REQUEST),
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
     * Sets up all fragment views with game information
     */
    private fun initialDataSetup(game: Game) {
        title_text_view.text = game.title
        if (currentGame.isFavourite!!) {
            favourite_image_button.setImageDrawable(resources.getDrawable(R.drawable.ic_star_yellow_48dp, null))
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
                    dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    /**
     * AlertDialog OnClickListener to delete the currently selected game
     */
    private val alertPositiveOnClick = DialogInterface.OnClickListener { _, _ ->
        viewModel.delete(currentGame)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(this@GameDetailFragment.id, GameListFragment.newInstance())
        transaction.commit()
    }

    /**
     * OnClickListener for the star ImageButton to favourite the Game
     */
    private val favouriteOnClick = View.OnClickListener {
        if (currentGame.isFavourite!!) {
            favourite_image_button
                .setImageDrawable(resources.getDrawable(R.drawable.ic_star_border_yellow_48dp, null))
            currentGame.isFavourite = false
        } else {
            favourite_image_button
                .setImageDrawable(resources.getDrawable(R.drawable.ic_star_yellow_48dp, null))
            currentGame.isFavourite = true
            Toast.makeText(context, getString(R.string.toast_favourite), Toast.LENGTH_SHORT).show()
        }
        viewModel.update(currentGame)
    }
}