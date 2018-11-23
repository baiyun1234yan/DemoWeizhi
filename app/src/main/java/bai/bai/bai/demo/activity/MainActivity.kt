package bai.bai.bai.demo.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.Toast

import bai.bai.bai.demo.R
import bai.bai.bai.demo.kt.KeyBoardLinearLayout
import bai.bai.bai.demo.view.MorePopupWindow
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 */
class MainActivity : Activity(), KeyBoardLinearLayout.KeyBoartTouchListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kbll_keyboard_kt.setKeyBoartTouchListener(this)
        iv_more.setOnClickListener(this)

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View?) {
        var popupWindow = MorePopupWindow(this)
        popupWindow!!.showWindow()
    }


    override fun onNumberTouch(inputNumber: String) {
        Toast.makeText(this@MainActivity, "我点击了数字:$inputNumber", Toast.LENGTH_SHORT).show()
    }

    override fun onDelTouch() {
        Toast.makeText(this@MainActivity, "我点击了删除按钮", Toast.LENGTH_SHORT).show()
    }

    override fun onOkTouch() {
        Toast.makeText(this@MainActivity, "我点击了确定按钮", Toast.LENGTH_SHORT).show()
    }
}
