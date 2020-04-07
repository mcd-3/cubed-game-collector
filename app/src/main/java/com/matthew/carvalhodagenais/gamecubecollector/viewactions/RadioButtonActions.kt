package com.matthew.carvalhodagenais.gamecubecollector.viewactions

import android.app.Activity
import android.util.Log
import android.view.View

class RadioButtonActions {

    fun test(view: View, activity: Activity) {
        Log.e("BUTTON", view.id.toString())
        activity.recreate()
    }
}