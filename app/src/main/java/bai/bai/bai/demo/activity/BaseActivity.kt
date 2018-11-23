package bai.bai.bai.demo.activity

import android.app.Activity
import android.os.Bundle
import android.view.View

abstract class BaseActivity : Activity() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()

    }

    abstract fun initListener()

    override fun onClick(v: View?) {

    }
}