package com.palm.newbenefit.ApiConfig

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormater {
    val currentTime: String
        get() {
            val curFormater = SimpleDateFormat("h:mm:ss a")
            return curFormater.format(Calendar.getInstance().time)
        }

    fun getTotalTime(startTime: String?): Long {
        val curFormater = SimpleDateFormat("h:mm:ss a")
        val currentTime = currentTime
        var time: Long = 0
        try {
            val sTime = curFormater.parse(startTime)
            val cTime = curFormater.parse(currentTime)
            val dif = cTime.time - sTime.time
            time = (dif / 1000)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    @JvmStatic
    fun getFormatedDate(dateNew: Date?): String {
        val curFormater = SimpleDateFormat("dd-MM-yyyy")
        return curFormater.format(dateNew)
    }

    fun getFormatedDate1(dateNew: Date?): String {
        val curFormater = SimpleDateFormat("yyyy/MM/dd")
        return curFormater.format(dateNew)
    }

    fun stringTodate(datestr: String?): Date? {
        val curFormater = SimpleDateFormat("dd/MM/yyyy")
        return try {
            curFormater.parse(datestr)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    val currentDateTime: String
        get() {
            val curFormater =
                SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            return curFormater.format(Calendar.getInstance().time)
        }

    val currentDate: String
        get() {
            val curFormater = SimpleDateFormat("MM/dd/yyyy")
            return curFormater.format(Calendar.getInstance().timeInMillis)
        }

    val currentDate1: String
        get() {
            val curFormater = SimpleDateFormat("dd")
            return curFormater.format(Calendar.getInstance().time)
        }

    fun getReduceddate(s: String?): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(s)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DAY_OF_MONTH,
            -1
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        val sdf1 = SimpleDateFormat("dd/MM/yyyy")
        //Log.d("The Date is",""+output);
        return sdf1.format(c.time)
    }

    fun getReduceddate1(s: String?): String {
        val sdf = SimpleDateFormat("dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(s)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        c.add(
            Calendar.DAY_OF_MONTH,
            -1
        ) // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        val sdf1 = SimpleDateFormat("dd")
        //Log.d("The Date is",""+output);
        return sdf1.format(c.time)
    }
}