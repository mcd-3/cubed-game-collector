package com.matthew.carvalhodagenais.gamecubecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.matthew.carvalhodagenais.gamecubecollector.ui.GameListFragment
import com.matthew.carvalhodagenais.gamecubecollector.ui.SettingsFragment
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameDetailViewModel
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameAddEditViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var gameDetailViewModel: GameDetailViewModel
    private lateinit var gameListViewModel: GameListViewModel
    private lateinit var gameAddEditViewModel: GameAddEditViewModel

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise ViewModels
        initViewModels()

        //Set NavigationDrawer
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this,
            main_activity_drawer_layout,
            R.string.drawer_layout_open,
            R.string.drawer_layout_close)
        main_activity_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        main_activity_navigation_view.setNavigationItemSelectedListener(this)
        main_activity_navigation_view.setCheckedItem(R.id.nav_games)

        //Set initial fragment
        replaceFragment(GameListFragment.newInstance(), GameListFragment.FRAGMENT_TAG)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

    //TODO: Clear the currently selected game when back is clicked at Detail frag
    override fun onBackPressed() {
        if (main_activity_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_activity_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //TODO: Edit what each thing does
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_games -> replaceFragment(GameListFragment.newInstance(), GameListFragment.FRAGMENT_TAG)
            R.id.nav_consoles -> Toast.makeText(this, "Consoles", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_accessories -> Toast.makeText(this, "Accessories", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_favourites -> Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show()//frag transaction
            //Maybe replace this with addFragment()
            R.id.nav_settings -> addFragment(SettingsFragment.newInstance(), SettingsFragment.FRAGMENT_TAG)
            R.id.nav_about -> Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()//frag transaction
        }
        main_activity_drawer_layout.closeDrawer(GravityCompat.START)
        gameAddEditViewModel.clearCurrentlySelectedGame()
        return true
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

    private fun replaceFragment(fragment: Fragment, fragmentTag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout,
            fragment,
            fragmentTag)
            .commit()
    }

    private fun addFragment(fragment: Fragment, fragmentTag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout,
            fragment,
            fragmentTag)
            .addToBackStack(fragmentTag)
            .commit()
    }
}
