package bai.bai.bai.demo.activity

import android.inputmethodservice.KeyboardView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import bai.bai.bai.demo.R
import bai.bai.bai.demo.view.KeyBoardLinearLayout
import kotlinx.android.synthetic.main.activity_java_view_test.*

class JavaViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_view_test)

        val keyboard = findViewById<KeyBoardLinearLayout>(R.id.keyboard)
        keyboard.setKeyBoartTouchListener(object : KeyBoardLinearLayout.KeyBoartTouchListener {
            override fun onNumberTouch(inputNumber: String) {

            }

            override fun onDelTouch() {

            }

            override fun onOkTouch() {

            }
        })

    }
}
