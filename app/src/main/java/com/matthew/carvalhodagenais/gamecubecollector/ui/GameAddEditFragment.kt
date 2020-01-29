package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_add_edit.*

class GameAddEditFragment: Fragment() {

    private lateinit var viewModel: GameViewModel

    companion object {

        private const val REQUEST_CODE: String = "GameAddEditFragment.REQUEST_CODE"
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.GameAddEditFragment"
        const val ADD_REQUEST: Int = 1
        const val EDIT_REQUEST: Int = 2

        fun newInstance(request: Int): GameAddEditFragment {
            val fragment =
                GameAddEditFragment()
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
            if (title_edit_text.text?.trim().toString().isEmpty()) {
                Toast.makeText(context,
                    getString(R.string.toast_no_title_warning),
                    Toast.LENGTH_SHORT).show()
            } else {
                saveGame()
                val transaction =
                    activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(this@GameAddEditFragment.id,
                    GameListFragment.newInstance(),
                    GameListFragment.FRAGMENT_TAG
                )
                transaction.commit()
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Saves the game to the database by grabbing all values from the inputs
     */
    private fun saveGame() {
//        val g: Game = Game(title_edit_text.text.toString()).apply {
//            developers = setNullIfEmptyString(developer_edit_text.text.toString())
//            publishers = setNullIfEmptyString(publisher_edit_text.text.toString())
//            releaseDate = null
//            regionId = null//condition_edit_text.text.toString().toInt()
//            boughtDate = null
//            condition = null//condition_edit_text.text.toString().toInt()
//            pricePaid = null//price_paid_edit_text.text.toString().toDouble()
//            hasCase = true
//            hasManual = true
//        }
//        g.isFavourite = false
//        g.imagePath = ""
//        viewModel.insert(g)
    }

    private fun setNullIfEmptyString(string: String): String? =
        if (string.trim().isEmpty()) null else string

}