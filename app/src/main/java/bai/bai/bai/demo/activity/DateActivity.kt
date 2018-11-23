package bai.bai.bai.demo.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import kotlinx.android.synthetic.main.activity_date.*
import java.text.SimpleDateFormat
import java.util.*


class DateActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        btn_date_0.setOnClickListener(this)
        btn_date_8.setOnClickListener(this)
        btn_date_is_same.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_date_0 -> {
                getGMTTime()
            }

            R.id.btn_date_8 -> {
                getTime8()
            }

            R.id.btn_date_is_same -> {
                isToday()
            }

        }
    }

    private fun isToday() {
        val todayFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        todayFormat.timeZone = TimeZone.getTimeZone("GMT")
        val dataStr = todayFormat.format(Date())
        val data = todayFormat.parse(dataStr)
        Log.d("baibai", "isToday - today = $dataStr, 零时区具体秒数为 ：${data.time}")

        val onedayFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        onedayFormat.timeZone = TimeZone.getTimeZone("GMT+08")
        val dataStr1 = onedayFormat.format(Date())
        val data1 = onedayFormat.parse(dataStr1)
        Log.d("baibai", "isToday - today = $dataStr1, 东八区具体秒数为 ：${data1.time}")

    }

    /**
     * 获取零时区时间
     */
    private fun getGMTTime() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val dataStr = sdf.format(Date())
        val data1 = sdf.parse(dataStr)
        Log.d("baibai", "零时区时间 ：$dataStr, 秒数是 : ${data1.time}")
    }

    /**
     * 获取东八区时间
     */
    private fun getTime8() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT+08")
        val dataStr = sdf.format(Date())
        val data1 = sdf.parse(dataStr)
        Log.d("baibai", "东八区时间 ：$dataStr, 秒数是 : ${data1.time}")
    }

    /**
     * 获取零时区时间
     */
    private fun getGMTDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(Date())
    }

    /**
     * 是否相差多少小时
     */
    private fun isPassHour(startDate: String, endDate: String, timespan: Int): Boolean {
        val sdf = SimpleDateFormat("yyyy-M-d HH:mm:ss", Locale.getDefault())
        val start = sdf.parse(startDate)
        val end = sdf.parse(endDate)
        val cha = end.time - start.time
        val result = cha * 1.0 / (1000 * 60 * 60)
        return result <= timespan
    }


}
