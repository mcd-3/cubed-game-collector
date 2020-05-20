package com.matthew.carvalhodagenais.cubedcollector.databinders.viewactions

import android.Manifest
import android.app.Activity
import com.matthew.carvalhodagenais.cubedcollector.helpers.CSVFileStorageHelper

class LinearLayoutActions {

    fun askWritePermissions(activity: Activity) {
        val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        activity.requestPermissions(
            permission,
            CSVFileStorageHelper.WRITE_EXTERNAL_PERMISSION_CODE
        )
    }

}