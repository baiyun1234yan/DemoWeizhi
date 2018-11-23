package bai.bai.bai.demo.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import kotlinx.android.synthetic.main.activity_assets_file.*
import java.nio.file.Files.exists
import java.io.BufferedReader
import java.io.InputStreamReader
import org.json.JSONObject
import org.json.JSONArray


class AssetsFileActivity : Activity(), View.OnClickListener {


    private val TAG = "AssetsFileActivity -> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_file)

        initListener()

    }

    private fun initListener() {
        btn_open_file.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_open_file -> {

//                getAssetsFile("kotlin-reference-chinese.pdf")
                getTxt()

//                val file = getFileStreamPath("kotlin-reference-chinese.pdf")
//                val setContentViewond = getFileStreamPath("kotlin-reference-chinese2.pdf")
//                Log.i(TAG, "onCreate: =path=" + file.absolutePath)
//                Log.i(TAG, "onCreate: =path=" + setContentViewond.absolutePath)
//                val absoluteFile = file.absoluteFile
//                if (absoluteFile.exists()) {
//                    Log.i(TAG, "onCreate: =文件存在=")
//                } else {
//                    Log.i(TAG, "onCreate: =文件不存在=")
//                }

            }

        }

    }

    private fun getAssetsFile(fileName: String): String {String
        try {
            val inputReader = InputStreamReader(resources.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var result = ""
            val line: String = bufReader.readLine()
            result += line
            Log.d(TAG, result)
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getTxt() {
        try {
            val isr = InputStreamReader(assets.open("logloglog.txt"), "UTF-8")
            val br = BufferedReader(isr)
            var line: String = br.readLine()
            var builder = StringBuilder()
            builder.append(line)
            br.close()
            isr.close()
//            val testjson = JSONObject(builder.toString())//builder读取了JSON中的数据。
//            val array = testjson.getJSONArray("data")         //从JSONObject中取出数组对象
//            for (i in 0 until array.length()) {
//                val role = array.getJSONObject(i)    //取出数组中的对象
//                val news_summary = role.getString("news_summary")
//                /*tv.append(role.getString("news_summary") + "\n ");*/
//            }
            tv_file_info.text = builder
            Log.d(TAG, "br = $br, line = $line, builder = $builder")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
