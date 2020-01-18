package com.matthew.carvalhodagenais.gamecubecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.matthew.carvalhodagenais.gamecubecollector.ui.GameListFragment
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set view model to be used across the app
        gameViewModel = ViewModelProviders.of(this,
            GameViewModelFactory(this.application)).get(GameViewModel::class.java)

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

        //Set initial fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout,
            GameListFragment.newInstance(),
            GameListFragment.FRAGMENT_TAG)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

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
            R.id.nav_games -> Toast.makeText(this, "Games", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_consoles -> Toast.makeText(this, "Consoles", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_accessories -> Toast.makeText(this, "Accessories", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_favourites -> Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_settings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()//frag transaction
            R.id.nav_about -> Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()//frag transaction
        }
        main_activity_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Get the GameViewModel so that we can share it instead of creating a new instance every time
     */
    fun getGameViewModel(): GameViewModel {
        return gameViewModel
    }
}
