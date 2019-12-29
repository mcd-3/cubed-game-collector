package com.matthew.carvalhodagenais.gamecubecollector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_detail.*

class GameDetailFragment: Fragment() {

    private lateinit var viewModel: GameViewModel

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
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialDataSetup(viewModel.getSelectedGame()!!)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_detail, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Sets up all fragment views with game information
     */
    private fun initialDataSetup(game: Game) {
        title_text_view.text = game.title
    }
}