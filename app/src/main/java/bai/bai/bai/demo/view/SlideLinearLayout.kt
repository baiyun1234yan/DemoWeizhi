package bai.bai.bai.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout

import bai.bai.bai.demo.R

/**
 * 自定义随手指滑动，控件跟着滑动
 */
class SlideLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), View.OnTouchListener {

    private var mHeight: Int = 0//布局的高度
    private val mContentView: LinearLayout//整个布局
    private val mSreenHeight: Int//整个屏幕的高度

    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.slide_linearlayout, null)
        contentView.findViewById<View>(R.id.iv_slide).setOnTouchListener(this)
        mContentView = contentView.findViewById(R.id.ll_slide)

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mSreenHeight = windowManager.defaultDisplay.height
        mHeight = (this.mSreenHeight * 967.0 / 1280.0).toInt()
        setViewHeight(mContentView, mHeight)
        addView(contentView)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_MOVE -> {
                mHeight = mSreenHeight - event.rawY.toInt()
                setViewHeight(mContentView, mHeight)
                mContentView.postInvalidate()
            }
            MotionEvent.ACTION_UP -> {
                mHeight = (this.mSreenHeight * 967.0 / 1280.0).toInt()
                setViewHeight(mContentView, mHeight)
            }
        }
        return true
    }

    /**
     * 设置子控件的高度
     */
    private fun setViewHeight(view: View, height: Int) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.height = height
        view.layoutParams = params
    }

}
