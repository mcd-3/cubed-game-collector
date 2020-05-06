package com.matthew.carvalhodagenais.cubedcollector

import com.matthew.carvalhodagenais.cubedcollector.helpers.DateHelper
import org.junit.Test
import java.util.*

/**
 * Tests to see whether or not the dates are being properly stringified
 */
class DateToStringTest {

    @Test
    fun test_date_to_string() {
        // Create a date
        val calJan = Calendar.getInstance()
        calJan.set(2020, 0, 1)
        val calJun = Calendar.getInstance()
        calJun.set(2020, 5, 15)
        val calDec = Calendar.getInstance()
        calDec.set(2020, 11, 31)

        // Create date string
        val strJan = DateHelper.createDateString(calJan.time)
        val strJun = DateHelper.createDateString(calJun.time)
        val strDec = DateHelper.createDateString(calDec.time)

        // Check if the date was created correctly
        println("Date String: $strJan")
        assert(strJan == "1/1/2020")
        println("Date String: $strJun")
        assert(strJun == "15/6/2020")
        println("Date String: $strDec")
        assert(strDec == "31/12/2020")

        println("Test Successful!")
    }

}