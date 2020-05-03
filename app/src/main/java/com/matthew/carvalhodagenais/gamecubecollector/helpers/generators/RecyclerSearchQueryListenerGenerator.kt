package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import android.view.MenuItem
import android.widget.SearchView
import com.matthew.carvalhodagenais.gamecubecollector.adapters.AccessoryListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.adapters.ConsoleListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter

class RecyclerSearchQueryListenerGenerator {

    private lateinit var gameRecyclerAdapter: GameListRecyclerAdapter
    private lateinit var consoleRecyclerAdapter: ConsoleListRecyclerAdapter
    private lateinit var accessoryRecyclerAdapter: AccessoryListRecyclerAdapter

    fun generateQueryTextListener(
        glra: GameListRecyclerAdapter
    ): SearchView.OnQueryTextListener {
        gameRecyclerAdapter = glra
        return searchGameQueryTextListener
    }

    fun generateQueryTextListener(
        clra: ConsoleListRecyclerAdapter
    ): SearchView.OnQueryTextListener {
        consoleRecyclerAdapter = clra
        return searchConsoleQueryTextListener
    }

    fun generateQueryTextListener(
        alra: AccessoryListRecyclerAdapter
    ): SearchView.OnQueryTextListener {
        accessoryRecyclerAdapter = alra
        return searchAccessoryQueryTextListener
    }

    /**
     * Generates a SearchExpandListener
     */
    fun generateExpandListener(): MenuItem.OnActionExpandListener {
        return searchExpandListener
    }

    /**
     * OnQueryTextListener for the search menu item.
     * Allows a user to search for a specific game
     */
    private var searchGameQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            gameRecyclerAdapter.filter.filter(newText)
            return false
        }
        override fun onQueryTextSubmit(query: String?): Boolean = false
    }

    /**
     * OnQueryTextListener for the search menu item.
     * Allows a user to search for a specific console
     */
    private var searchConsoleQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            consoleRecyclerAdapter.filter.filter(newText)
            return false
        }
        override fun onQueryTextSubmit(query: String?): Boolean = false
    }

    /**
     * OnQueryTextListener for the search menu item.
     * Allows a user to search for a specific accessory
     */
    private var searchAccessoryQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            accessoryRecyclerAdapter.filter.filter(newText)
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