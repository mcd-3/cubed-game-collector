package com.matthew.carvalhodagenais.cubedcollector.helpers

import android.os.Environment
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Condition
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Game
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Region
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CSVFileStorageHelper {
    companion object {
        const val WRITE_EXTERNAL_PERMISSION_CODE = 11

        /**
         * Exports a list of games to an Excel CSV file
         *
         * @param gameList List<Game>
         */
        fun exportToCSV(gameList: List<Game>?) {
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
                            "Manual" + "\n")
                    if ( gameList != null && gameList.isNotEmpty()) {
                        gameList.forEach {
                            fw.append(it.title.replace(",", "\"\",\"\"") + ",")
                            fw.append(getNoneIfNull(it.developers).replace(",", "\"\",\"\"") + ",")
                            fw.append(getNoneIfNull(it.publishers).replace(",", "\"\",\"\"") + ",")
                            fw.append(getNoneIfNull(DateHelper.createDateString(it.releaseDate)) + ",")
                            fw.append(Region.getRegionName(it.regionId) + ",")
                            fw.append(getNoneIfNull(DateHelper.createDateString(it.boughtDate)) + ",")
                            fw.append(Condition.getConditionName(it.conditionId) + ",")
                            fw.append(getNoneIfNull(it.pricePaid) + ",")
                            fw.append(boolToStr(it.hasCase) + ",")
                            fw.append(boolToStr(it.hasManual))
                            fw.append("\n")
                        }
                    }
                    fw.close()
                }.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         * Returns a string if null
         *
         * @param str String
         * @return String
         */
        private fun getNoneIfNull(str: String?): String {
            return if (str == null || str.trim() == "") "None" else str
        }

        /**
         * Returns a string if null
         *
         * @param double Double
         * @return String
         */
        private fun getNoneIfNull(double: Double?): String {
            return if (double == null) "None" else "$$double"
        }

        /**
         * Returns "Yes" if true and "No" if false
         *
         * @param bool Boolean
         * @return String
         */
        private fun boolToStr(bool: Boolean?): String {
            if (bool == null) {
                return "No"
            } else {
                return if (bool) "Yes" else "No"
            }
        }
    }
}