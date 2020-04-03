package com.matthew.carvalhodagenais.gamecubecollector

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.ui.ImageSelectDialogFragment
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.*

class MainActivity : AppCompatActivity(), ImageSelectDialogFragment.ImageSelectDialogInterface{//, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var gameDetailViewModel: GameDetailViewModel
    private lateinit var gameListViewModel: GameListViewModel
    private lateinit var gameAddEditViewModel: GameAddEditViewModel
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the theme
        setTheme(R.style.CubedTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Initialise ViewModels
        initViewModels()

        //Set NavigationDrawer
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_games, R.id.nav_favourites, R.id.nav_settings
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

    override fun changeImageBitmap(bitmap: Bitmap) {
        cover_art_image_view?.setImageBitmap(bitmap)
    }


}
