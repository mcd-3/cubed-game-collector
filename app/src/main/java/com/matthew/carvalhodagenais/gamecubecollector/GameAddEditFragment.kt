package com.matthew.carvalhodagenais.gamecubecollector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GameAddEditFragment: Fragment() {

    companion object {

        val ADD_REQUEST: Int = 1
        val EDIT_REQUEST: Int = 2

        fun newInstance() {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_game_add_edit, container, false)
        return view
    }
}