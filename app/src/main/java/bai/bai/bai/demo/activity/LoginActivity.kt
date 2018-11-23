package bai.bai.bai.demo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import bai.bai.bai.demo.R
import bai.bai.bai.demo.view.PopWindow
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录界面
 */
class LoginActivity : Activity(), View.OnClickListener {

    var mPopupWindow: PopWindow? = null
    var mRlUserNumber: RelativeLayout? = null
    var mEtUserNumber: EditText? = null
    var mIvMore: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initData()
        initListener()


    }


    private fun initListener() {

        btn_login.setOnClickListener(this)
        iv_user_number_more.setOnClickListener(this)
    }

    fun initData() {
        mRlUserNumber = findViewById<View>(R.id.rl_user_number) as RelativeLayout
        mEtUserNumber = findViewById<View>(R.id.et_user_number) as EditText
        mIvMore = findViewById<View>(R.id.iv_user_number_more) as ImageView
        mPopupWindow = PopWindow()

        mEtUserNumber!!.imeOptions = EditorInfo.IME_ACTION_SEND
        mEtUserNumber!!.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            Log.d("ActionListener", "点击了键盘111")
            if (actionId == EditorInfo.IME_ACTION_GO) {
                Log.d("ActionListener", "点击了键盘上的回车111")
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })


        mEtUserNumber!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            Log.d("setOnKeyListener", "点击了键盘222")
            if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
                Log.d("setOnKeyListener", "点击了键盘上的回车222")
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        Log.d("dispatchKeyEvent", "点击了键盘333")
        if (event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.d("dispatchKeyEvent", "点击了键盘上的回车333")
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_login -> startActivity(Intent(this, MainActivity::class.java))
            R.id.iv_user_number_more -> {
                var datas: List<String> = listOf("1111111", "22222", "33333", "4444444", "555555", "1111111", "22222", "33333", "4444444", "555555")
                mPopupWindow!!.showWindow(this, mRlUserNumber, datas, mEtUserNumber)

            }
        }
    }


}
