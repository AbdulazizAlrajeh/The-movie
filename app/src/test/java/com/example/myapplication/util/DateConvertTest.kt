package com.example.myapplication.util

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
    fun convertDate(){
        val dateconvert = dateUtil.convertDataUsing_YY_MM_dd_("2019-09-10")
        assertEquals("2019/09/10",dateconvert)

    }

}