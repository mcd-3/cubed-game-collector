package com.matthew.carvalhodagenais.cubedcollector.helpers

import android.os.Environment
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CSVFileStorageHelper {
    companion object {
        const val WRITE_EXTERNAL_PERMISSION_CODE = 11

        suspend fun exportToCSV() {//(gameList: List<Game>) {
            val directory: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val fileName = "${directory}/Cubed_My_Game_List.csv"
            try {
                // Bypass blocking calls using Thread
                val t = Thread {
                    val fw = FileWriter(fileName)
                    fw.append("Title," +
                            "Developer," +
                            "Publisher," +
                            "Release Date," +
                            "Region," +
                            "Date Bought," +
                            "Condition," +
                            "Price Paid," +
                            "Case," +
                            "Manual")
                    fw.close()
                }.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}