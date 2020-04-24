package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import androidx.navigation.NavController
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.AccessoryListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.adapters.ConsoleListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Accessory
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.ui.FavouriteGameListFragmentDirections
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel

class RecyclerAdapterItemClickGenerator() {

    companion object {
        private const val FROM_FAVOURITE_REQUEST: Int = 1
    }

    fun generate(
        viewModel: AccessoryDetailViewModel,
        navController: NavController
    ): AccessoryListRecyclerAdapter.ItemOnClickListener {
        val itemOnClick = object: AccessoryListRecyclerAdapter.ItemOnClickListener {
            override fun onItemClick(accessory: Accessory) {
                viewModel.setSelectedAccessory(accessory)
                //navController.navigate(R.id.action_nav_consoles_to_consoleDetailFragment)
            }
        }
        return itemOnClick
    }

    fun generate(
        viewModel: ConsoleDetailViewModel,
        navController: NavController
    ): ConsoleListRecyclerAdapter.ItemOnClickListener {
        val itemOnClick = object: ConsoleListRecyclerAdapter.ItemOnClickListener {
            override fun onItemClick(console: Console) {
                viewModel.setSelectedConsole(console)
                navController.navigate(R.id.action_nav_consoles_to_consoleDetailFragment)
            }
        }
        return itemOnClick
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
}