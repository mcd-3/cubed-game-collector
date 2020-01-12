package com.matthew.carvalhodagenais.gamecubecollector

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*

class GameAddEditFragment: Fragment() {

    private lateinit var viewModel: GameViewModel

    companion object {

        private const val REQUEST_CODE: String = "GameAddEditFragment.REQUEST_CODE"
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.GameAddEditFragment"
        const val ADD_REQUEST: Int = 1
        const val EDIT_REQUEST: Int = 2

        fun newInstance(request: Int): GameAddEditFragment {
            val fragment = GameAddEditFragment()
            val args = Bundle()
            args.putInt(REQUEST_CODE, request)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_game_add_edit, container, false)
        setHasOptionsMenu(true)
        viewModel = (activity as MainActivity).getGameViewModel()
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        activity!!.menuInflater.inflate(R.menu.menu_game_add_edit, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.menu_save -> {
            Toast.makeText(context, "Save coming soon!", Toast.LENGTH_SHORT).show()
            saveGame()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveGame() {
        //TODO: Save the game to DB
        val g: Game = Game("New Super Mario Bros Wii").apply {
            developers = setNullIfEmptyString(developer_edit_text.text.toString())
            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
            releaseDate = null
            regionId = 1
            boughtDate = null
            condition = 2
            pricePaid = 10.00
            hasCase = true
            hasManual = true
        }
        g.isFavourite = false
        g.imagePath = ""
        viewModel.insert(g)
    }

    private fun setNullIfEmptyString(string: String): String? =
        if (string.trim().isEmpty()) null else string


}