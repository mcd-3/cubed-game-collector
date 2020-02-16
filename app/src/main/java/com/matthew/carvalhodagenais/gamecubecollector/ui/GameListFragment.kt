package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Ignore
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameListViewModel
import kotlinx.android.synthetic.main.fragment_game_list.*

class GameListFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameListFragment"
        fun newInstance() =
            GameListFragment()
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

        val recyclerAdapter = GameListRecyclerAdapter()

        game_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity!!.applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getGameListViewModel().getAllGames()
            .observe(viewLifecycleOwner, Observer<List<Game>> {
                recyclerAdapter.submitList(it)
                recyclerAdapter.setItemOnClickListener(itemOnClick)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_list, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_add -> {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(this@GameListFragment.id,
                GameAddEditFragment.newInstance(
                    GameAddEditFragment.ADD_REQUEST
                ),
                GameAddEditFragment.FRAGMENT_TAG)
                .addToBackStack(null)
            transaction.commit()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * OnClick for each RecyclerView item.
     * Changes the fragment to the game's details
     */
    private var itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
        override fun onItemClick(game: Game) {
            (activity as MainActivity).getGameDetailViewModel().setSelectedGame(game)
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(this@GameListFragment.id,
                GameDetailFragment.newInstance(),
                GameDetailFragment.FRAGMENT_TAG)
                .addToBackStack(null)
            transaction.commit()
        }
    }
}
