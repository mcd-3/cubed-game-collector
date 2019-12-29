package com.matthew.carvalhodagenais.gamecubecollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.matthew.carvalhodagenais.gamecubecollector.data.CollectorDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.GameRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set view model to be used across the app
        gameViewModel = ViewModelProviders.of(this,
            GameViewModelFactory(this.application)).get(GameViewModel::class.java)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame_layout,
            GameListFragment.newInstance(),
            GameListFragment.FRAGMENT_TAG)
            .commit()
    }

    /**
     * Get the GameViewModel so that we can share it instead of creating a new instance every time
     */
    fun getGameViewModel(): GameViewModel {
        return gameViewModel
    }
}
