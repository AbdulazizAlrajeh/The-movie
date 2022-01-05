package com.example.myapplication.util

import android.icu.text.DateIntervalFormat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DateConvertTest{

    private lateinit var dateUtil:DateConvert

    @Before
    fun setUp(){
        dateUtil = DateConvert()

    }

    @Test
    fun convertDateCorrectReturn(){
        val dateconvert = dateUtil.convertDataUsing_YY_MM_dd_("2019-09-10")
        assertEquals("2019/09/10",dateconvert)
    }
    @Test
    fun convertDateErrorReturn(){
        val dateconvert = dateUtil.convertDataUsing_YY_MM_dd_("2019-09-10")
        assertEquals(dateconvert,dateconvert)

    }

}