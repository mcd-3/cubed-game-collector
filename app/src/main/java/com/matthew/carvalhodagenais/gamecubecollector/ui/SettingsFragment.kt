package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentGameDetailBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentSettingsBinding
import com.matthew.carvalhodagenais.gamecubecollector.viewactions.ImageButtonActions
import com.matthew.carvalhodagenais.gamecubecollector.viewactions.RadioButtonActions
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: Fragment() {

    companion object {
        const val FRAGMENT_TAG =
            "com.matthew.carvalhodagenais.gamecubecollector.ui.SettingsFragment"
        fun newInstance() =
            SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater, R.layout.fragment_settings, container, false
        ).apply {
            this.act = (activity as MainActivity)
            this.radioButtonActions = RadioButtonActions()
        }
        return binding.root//inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        themes_radio_group.setOnCheckedChangeListener(radioOnClickListener)
    }

    private val radioOnClickListener = RadioGroup.OnCheckedChangeListener {radio, radioId ->
        when (radioId) {
            R.id.radio_theme_cubed -> Toast.makeText(context, "Cubed", Toast.LENGTH_SHORT).show()
            R.id.radio_theme_cubed_dark -> Toast.makeText(context, "CubedDark", Toast.LENGTH_SHORT).show()
            R.id.radio_theme_night -> Toast.makeText(context, "Night", Toast.LENGTH_SHORT).show()
            R.id.radio_theme_dolphin -> Toast.makeText(context, "Dolphin", Toast.LENGTH_SHORT).show()
            R.id.radio_theme_player_selection -> Toast.makeText(context, "PS", Toast.LENGTH_SHORT).show()
            R.id.radio_theme_silver_player -> Toast.makeText(context, "Silver", Toast.LENGTH_SHORT).show()
        }
    }

}