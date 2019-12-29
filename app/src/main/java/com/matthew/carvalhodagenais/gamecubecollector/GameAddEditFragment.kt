package com.matthew.carvalhodagenais.gamecubecollector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GameAddEditFragment: Fragment() {

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
        return view
    }
}