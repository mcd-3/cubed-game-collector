package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.helpers.ItemTouchHelperGenerator
import kotlinx.android.synthetic.main.fragment_game_list.*

class GameListFragment : Fragment() {

    private lateinit var recyclerAdapter: GameListRecyclerAdapter

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

        recyclerAdapter = GameListRecyclerAdapter()

        game_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getGameListViewModel().getAllGames()
            .observe(viewLifecycleOwner, Observer<List<Game>> {
                recyclerAdapter.submitList(it)
                recyclerAdapter.setSearchableList(it)
                recyclerAdapter.setItemOnClickListener(itemOnClick)
        })

        // Make each item slide
        ItemTouchHelper(ItemTouchHelperGenerator().generate()).attachToRecyclerView(game_list_recycler_view)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_game_list, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(searchQueryTextListener)
        searchItem.setOnActionExpandListener(searchExpandListener)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_add -> {
            val action =
                GameListFragmentDirections.actionGameListFragmentToGameAddEditFragment(
                    GameAddEditFragment.ADD_REQUEST
                )
            findNavController().navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * OnQueryTextListener for the search menu item.
     * Allows a user to search for a specific game
     */
    private var searchQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            recyclerAdapter.filter.filter(newText)
            return false
        }
        override fun onQueryTextSubmit(query: String?): Boolean = false
    }

    /**
     * OnActionExpandListener for the search menu item.
     */
    private var searchExpandListener = object: MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean = true
    }

    /**
     * OnClick for each RecyclerView item.
     * Changes the fragment to the game's details
     */
    private var itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
        override fun onItemClick(game: Game) {
            (activity as MainActivity).getGameDetailViewModel().setSelectedGame(game)
            findNavController().navigate(R.id.action_gameListFragment_to_gameDetailFragment)
        }
    }
}
