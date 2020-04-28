package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.databinding.FragmentSettingsBinding
import com.matthew.carvalhodagenais.gamecubecollector.databinders.viewactions.RadioButtonActions
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
            this.viewModel = (activity as MainActivity).getSettingsViewModel()
        }
        binding.deleteAllDataLinearLayout.setOnClickListener(showDialogOnClickListener)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPrefs = activity?.getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE)
        val theme = sharedPrefs?.getInt(
            getString(R.string.shared_preference_theme_key),
            getString(R.string.shared_preference_theme_cubed).toInt())
        when (theme) {
            getString(R.string.shared_preference_theme_cubed).toInt() -> radio_theme_cubed.isChecked = true
            getString(R.string.shared_preference_theme_cubed_dark).toInt() -> radio_theme_cubed_dark.isChecked = true
            getString(R.string.shared_preference_theme_cubed_night).toInt() -> radio_theme_night.isChecked = true
            getString(R.string.shared_preference_theme_cubed_dolphin).toInt() -> radio_theme_dolphin.isChecked = true
            getString(R.string.shared_preference_theme_cubed_ps).toInt() -> radio_theme_player_selection.isChecked = true
            getString(R.string.shared_preference_theme_cubed_pss).toInt() -> radio_theme_silver_player.isChecked = true
            else -> radio_theme_cubed.isChecked = true
        }
    }

    /**
     * Prompts the user for whether or not the game will be deleted
     */
    private val showDialogOnClickListener = View.OnClickListener {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(getString(R.string.delete_all_data_alert_title))
            .setMessage(getString(R.string.delete_all_data_alert_body))
            .setPositiveButton(getString(R.string.delete_alert_positive),
                alertPositiveOnClick)
            .setNegativeButton(getString(R.string.delete_alert_negative)){
                    dialog, _ -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    /**
     * AlertDialog OnClickListener to delete all in-app data
     */
    private val alertPositiveOnClick = DialogInterface.OnClickListener { _, _ ->
        (activity as MainActivity).getSettingsViewModel().deleteAllData()
        Snackbar.make(view!!, "All in-app data deleted", Snackbar.LENGTH_SHORT).show()
    }
}