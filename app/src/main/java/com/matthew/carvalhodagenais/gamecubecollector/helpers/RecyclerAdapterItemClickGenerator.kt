package com.matthew.carvalhodagenais.gamecubecollector.helpers

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel

class RecyclerAdapterItemClickGenerator() {

    /**
     * OnClick for each RecyclerView Game item.
     * Changes the fragment to the game's details
     */
    fun generate(
        viewModel: GameDetailViewModel,
        navController: NavController
    ): GameListRecyclerAdapter.ItemOnClickListener {
        val itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
            override fun onItemClick(game: Game) {
                viewModel.setSelectedGame(game)
                navController.navigate(R.id.action_gameListFragment_to_gameDetailFragment)
            }
        }
        return itemOnClick
    }

    /**
     * OnClick for each RecyclerView item.
     * Changes the fragment to the game's details
     */
//    private var itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
//        override fun onItemClick(game: Game) {
//            (activity as MainActivity).getGameDetailViewModel().setSelectedGame(game)
//            findNavController().navigate(R.id.action_gameListFragment_to_gameDetailFragment)
//        }
//    }

}