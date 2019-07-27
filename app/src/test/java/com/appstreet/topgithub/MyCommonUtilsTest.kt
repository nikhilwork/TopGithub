package com.appstreet.topgithub

import com.appstreet.topgithub.utils.CommonUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import android.text.format.DateUtils



class MyCommonUtilsTest {
    @Test
    fun testCurrentDayValue() {
        val actual = CommonUtils.getCurrentDay()
        val expected = "Sat"
        assertEquals("Day Conversion Failed", expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun dateUtilsFormat_isCorrect() {
        // long epoc = 1446885450; //7th Nov 2015
        val epoc: Long = 1446985450 //8th Nov 2015
        val date = CommonUtils.epocSecondsToDate(epoc)
        assertEquals(
            "Date time in millis is wrong",
            epoc * 1000, date.getTime()
        )
        val day = CommonUtils.dateToDayDateString(date, true)
        assertEquals("Day is wrong", "SAT", day)
    }

}