package com.snappay.cashier.bizmodule.main.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import bai.bai.bai.demo.R

class CustomLayoutPaySuccess constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), View.OnClickListener {

    private var mPaySuccessListener: PaySuccessListener? = null

    private val mBtnInfo: Button
    private val mBtnReprint: Button
    private val mBtnFinish: Button

    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pay_layout_pay_success, null)
        mBtnInfo = contentView.findViewById(R.id.btn_info)//查看详情
        mBtnReprint = contentView.findViewById(R.id.btn_reprint)//重新打印
        mBtnFinish = contentView.findViewById(R.id.btn_finish)//完成
        mBtnInfo.setOnClickListener(this)
        mBtnReprint.setOnClickListener(this)
        mBtnFinish.setOnClickListener(this)
        addView(contentView)
    }

    /**
     * 显示动画
     */
    fun showAnim() {
//        transAnimViewVertical(context, mBtnInfo, 300f, mBtnInfo.translationY)
//        transAnimViewVertical(context, mBtnReprint, 600f, mBtnReprint.translationY)
//        transAnimViewVertical(context, mBtnFinish, 900f, mBtnFinish.translationY)
    }

    override fun onClick(v: View?) {
        if (mPaySuccessListener == null) return
        when (v!!.id) {
            R.id.btn_info -> mPaySuccessListener!!.onInfo()
            R.id.btn_reprint -> mPaySuccessListener!!.onReprint()
            R.id.btn_finish -> mPaySuccessListener!!.onFinish()
        }
    }

    interface PaySuccessListener {
        fun onInfo()

        fun onReprint()
        fun onFinish()
    }

    fun setPaySuccessListener(listener: PaySuccessListener) {
        mPaySuccessListener = listener
    }


}