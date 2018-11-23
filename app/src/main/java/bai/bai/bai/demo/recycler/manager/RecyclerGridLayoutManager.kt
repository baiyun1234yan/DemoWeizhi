package com.snappay.cashier.bizmodule.main.recycler

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * RecyclerView的网格布局
 */

class RecyclerGridLayoutManager(context: Context, adapter: RecyclerViewAdapter<*, *>, spanCount: Int) : GridLayoutManager(context, spanCount) {

    private var speedRatio: Double = 0.toDouble()

    init {
        this.spanSizeLookup = SectionedSpanSizeLookup(adapter, this)//设置heander
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val i = super.scrollVerticallyBy((speedRatio * dy).toInt(), recycler, state)
        return if (i == (speedRatio * dy).toInt()) {
            dy
        } else i
    }

    /**
     * 设置与手指滑动的速度比
     */
    fun setSpeedRatio(speedRatio: Double) {
        this.speedRatio = speedRatio
    }

    /**
     * 这个类是用来自定义每个item需要占据的空间（也可以理解为每个大标题header的宽度）
     */
    inner class SectionedSpanSizeLookup(adapter: RecyclerViewAdapter<*, *>, layoutManager: GridLayoutManager) : GridLayoutManager.SpanSizeLookup() {
        protected var adapter: RecyclerViewAdapter<*, *>? = null
        protected var layoutManager: GridLayoutManager? = null

        init {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        override fun getSpanSize(position: Int): Int {
            //header和footer占据的是全屏
            return if (adapter!!.isGroupHeaderForPosition(position)) {
                layoutManager!!.spanCount
            } else {
                1//其他默认1
            }
        }
    }

}
