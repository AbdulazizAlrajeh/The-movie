package com.example.myapplication.util

import android.os.Build
import androidx.annotation.RequiresApi

class DateConvert {


    /**
     * The function below is to convert time in YY-MM-dd to yyyy/MM/dd format
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDataUsing_YY_MM_dd_(time: String): String {
        return time.replace('-', '/')

    }
}