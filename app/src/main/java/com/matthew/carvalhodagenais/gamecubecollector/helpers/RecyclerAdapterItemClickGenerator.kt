package com.matthew.carvalhodagenais.gamecubecollector.helpers

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.ui.FavouriteGameListFragmentDirections
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel

class RecyclerAdapterItemClickGenerator() {

    companion object {
        private const val FROM_FAVOURITE_REQUEST: Int = 1
    }

    /**
     * OnClick for each RecyclerView Game item.
     * Changes the fragment to the game's details
     */
    fun generate(
        viewModel: GameDetailViewModel,
        navController: NavController,
        fromFavourite: Boolean
    ): GameListRecyclerAdapter.ItemOnClickListener {
        val itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
            override fun onItemClick(game: Game) {
                viewModel.setSelectedGame(game)
                if (!fromFavourite) {
                    navController.navigate(R.id.action_gameListFragment_to_gameDetailFragment)
                } else {
                    val action = FavouriteGameListFragmentDirections.actionNavFavouritesToGameDetailFragment(
                        FROM_FAVOURITE_REQUEST
                    )
                    navController.navigate(action)
                }
            }
        }
        return itemOnClick
    }

//        private var itemOnClick = object: GameListRecyclerAdapter.ItemOnClickListener {
//        override fun onItemClick(game: Game) {
//            (activity as MainActivity).getGameDetailViewModel().setSelectedGame(game)
//            val action = FavouriteGameListFragmentDirections.actionNavFavouritesToGameDetailFragment(
//                FROM_FAVOURITE_REQUEST
//            )
//            findNavController().navigate(action)
//        }
//    }
}