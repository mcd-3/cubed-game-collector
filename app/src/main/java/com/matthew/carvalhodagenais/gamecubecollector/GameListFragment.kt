package com.matthew.carvalhodagenais.gamecubecollector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_list.*

class GameListFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.GameListFragment"
        fun newInstance() = GameListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        viewModel = (activity as MainActivity).getGameViewModel()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerAdapter = GameListRecyclerAdapter()

        game_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity!!.applicationContext)
            adapter = recyclerAdapter
        }

        viewModel.getAllGames().observe(viewLifecycleOwner, Observer<List<Game>> {
            recyclerAdapter.submitList(it)
            recyclerAdapter.setItemOnClickListener(itemOnClick)
        })
    }

    /**
     * OnClick for each RecyclerView item.
     * Changes the fragment to the game's details
     */
    private var itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
        override fun onItemClick(game: Game) {
            viewModel.setSelectedGame(game)
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(this@GameListFragment.id,
                GameDetailFragment.newInstance(),
                GameDetailFragment.FRAGMENT_TAG)
                .addToBackStack(null)
            transaction.commit()
        }
    }
}
