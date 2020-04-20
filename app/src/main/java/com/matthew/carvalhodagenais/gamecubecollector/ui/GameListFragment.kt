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
import com.matthew.carvalhodagenais.gamecubecollector.helpers.RecyclerAdapterItemClickGenerator
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
            .observe(viewLifecycleOwner, Observer {

                if (it.count() < 1) {
                    no_games_title_text_view.text = getString(R.string.no_game_title)
                    no_games_subtitle_text_view.text = getString(R.string.no_game_subtitle)
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
                        false
                    )
                )
        })

        // Make each item slide
        ItemTouchHelper(
            ItemTouchHelperGenerator(context!!).generate(
                recyclerAdapter,
                (activity as MainActivity).getGameListViewModel()
            )
        ).attachToRecyclerView(game_list_recycler_view)
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
            (activity as MainActivity).getGameAddEditViewModel().clearCurrentlySelectedGame()
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
}
