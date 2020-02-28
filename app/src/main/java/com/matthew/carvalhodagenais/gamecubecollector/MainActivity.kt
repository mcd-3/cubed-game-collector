package com.matthew.carvalhodagenais.gamecubecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){//, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var gameDetailViewModel: GameDetailViewModel
    private lateinit var gameListViewModel: GameListViewModel
    private lateinit var gameAddEditViewModel: GameAddEditViewModel

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise ViewModels
        initViewModels()

        //Set NavigationDrawer
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_games, R.id.nav_settings
        ), main_activity_drawer_layout)
        setupActionBarWithNavController(navController, appBarConfig)
        main_activity_navigation_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    /**
     * Gets the GameDetailViewModel
     */
    fun getGameDetailViewModel(): GameDetailViewModel {
        return gameDetailViewModel
    }

    /**
     * Gets the GameListViewModel
     */
    fun getGameListViewModel(): GameListViewModel {
        return gameListViewModel
    }

    /**
     * Gets the GameAddEditViewModel
     */
    fun getGameAddEditViewModel(): GameAddEditViewModel {
        return gameAddEditViewModel
    }

    /**
     * Initialises all the ViewModels associated with this activity. Each ViewModel will be used
     * in its respective Fragment
     */
    private fun initViewModels() {
        gameListViewModel = ViewModelProvider(this, GameViewModelFactory(this.application))
            .get(GameListViewModel::class.java)
        gameDetailViewModel = ViewModelProvider(this, GameViewModelFactory(this.application)
        ).get(GameDetailViewModel::class.java)
        gameAddEditViewModel = ViewModelProvider(this, GameViewModelFactory(this.application))
            .get(GameAddEditViewModel::class.java)
    }
}
