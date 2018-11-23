package com.snappay.cashier.bizmodule.main.recycler

import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import bai.bai.bai.demo.recycler.adapter.MoreAdapter

/**
 * Created by Arrow on 2016/5/25.
 */
abstract class RecyclerItemMovedTouchListener(private val recyclerView: RecyclerView, private val adapter: MoreAdapter) : RecyclerView.OnItemTouchListener {

    private val mGestureDetector: GestureDetectorCompat

    init {
        this.mGestureDetector = GestureDetectorCompat(recyclerView.context,
                ItemTouchHelperGestureListener())
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        mGestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    abstract fun onItemClick(vh: RecyclerView.ViewHolder, groupPosition: Int, childPosition: Int)

    abstract fun onLongClick(vh: RecyclerView.ViewHolder, position: Int)

    private inner class ItemTouchHelperGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child)
                val pos = recyclerView.getChildAdapterPosition(child)
                //                Log.i("TAG", "---onLongPress--pos--" + pos);
                if (adapter.isGroupHeaderForPosition(pos)) {
                    return
                }
                onLongClick(vh, pos)
            }
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                val vh = recyclerView.getChildViewHolder(child)
                val pos = recyclerView.getChildAdapterPosition(child)
                //                Log.i("TAG", "---onSingleTapUp--pos--" + pos);
                val groupPosition = adapter.getGroupForPosition(pos)
                //                Log.i("TAG", "---onSingleTapUp--groupPosition--" + groupPosition);
                val childPosition = adapter.getChildItemForPosition(pos)
                //                Log.i("TAG", "---onSingleTapUp--childPosition--" + childPosition);
                if (adapter.isGroupHeaderForPosition(pos)) {
                    return false
                }
                onItemClick(vh, groupPosition, childPosition)
            }
            return true
        }
    }
}
