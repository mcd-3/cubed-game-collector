package com.matthew.carvalhodagenais.gamecubecollector.viewactions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.matthew.carvalhodagenais.gamecubecollector.R

class RadioButtonActions {

    /**
     * Sets the theme to be used in SharedPreferences when a RadioButton is clicked
     *
     * @param view
     * @param activity
     */
    @Suppress("ApplySharedPref")
    fun setTheme(view: View, activity: Activity) {
        val sharedPref = activity.getSharedPreferences(
            activity.getString(R.string.shared_preference_key),
            Context.MODE_PRIVATE)

        val keyInt = when (view.id) {
            R.id.radio_theme_cubed -> activity.getString(
                R.string.shared_preference_theme_cubed).toInt()
            R.id.radio_theme_cubed_dark -> activity.getString(
                R.string.shared_preference_theme_cubed_dark).toInt()
            R.id.radio_theme_night -> activity.getString(
                R.string.shared_preference_theme_cubed_night).toInt()
            R.id.radio_theme_dolphin -> activity.getString(
                R.string.shared_preference_theme_cubed_dolphin).toInt()
            R.id.radio_theme_player_selection -> activity.getString(
                R.string.shared_preference_theme_cubed_ps).toInt()
            R.id.radio_theme_silver_player -> activity.getString(
                R.string.shared_preference_theme_cubed_pss).toInt()
            else -> activity.getString(R.string.shared_preference_theme_cubed).toInt()
        }

        sharedPref.edit().putInt(
            activity.getString(R.string.shared_preference_theme_key), keyInt).commit()

        activity.recreate()
    }
}