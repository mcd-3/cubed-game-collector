package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.helpers.RecyclerAdapterItemClickGenerator
import kotlinx.android.synthetic.main.fragment_game_list.*

class FavouriteGameListFragment: Fragment() {

    private lateinit var recyclerAdapter: GameListRecyclerAdapter

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.FavouriteGameListFragment"
        fun newInstance() =
            FavouriteGameListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerAdapter = GameListRecyclerAdapter()

        game_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getGameListViewModel().getAllFavouriteGames()
            .observe(viewLifecycleOwner, Observer {

                if (it.count() < 1) {
                    no_games_title_text_view.text = getString(R.string.no_favourite_games_title)
                    no_games_subtitle_text_view.text = getString(R.string.no_favourite_games_subtitle)
                    no_games_layout.visibility = View.VISIBLE
                } else {
                    no_games_layout.visibility = View.INVISIBLE
                }

                recyclerAdapter.submitList(it)
                recyclerAdapter.setSearchableList(it)

                val onClickGenerator = RecyclerAdapterItemClickGenerator()
                recyclerAdapter.setItemOnClickListener(
                    onClickGenerator.generate(
                        (activity as MainActivity).getGameDetailViewModel(),
                        findNavController(),
                        true
                    )
                )
            })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        super.onPrepareOptionsMenu(menu)
    }
}