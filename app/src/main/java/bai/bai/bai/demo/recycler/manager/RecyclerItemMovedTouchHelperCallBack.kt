package com.snappay.cashier.bizmodule.main.recycler

import android.app.Service
import android.content.Context
import android.graphics.Color
import android.os.Vibrator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log

import java.util.Collections


/**
 * Created by Arrow on 2016/5/25.
 */
class RecyclerItemMovedTouchHelperCallBack(adapter: RecyclerViewAdapter<*, *>, private val context: Context) : ItemTouchHelper.Callback() {

    private var adapter: RecyclerViewAdapter<*, *>? = null

    private var onDragListener: OnDragListener? = null

    init {
        this.adapter = adapter
    }

    /**
     * 设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        //如果是列表类型的RecyclerView，拖拽只有UP、DOWN两个方向
        //如果是网格类型的则有UP、DOWN、LEFT、RIGHT四个方向
        val dragFlags: Int
        val swipeFlags: Int
        if (recyclerView.layoutManager is GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            swipeFlags = 0
        } else {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            //TODO 注意
            swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        }
        return isEnableDrag(viewHolder, dragFlags, swipeFlags)
    }

    fun isEnableDrag(viewHolder: RecyclerView.ViewHolder, dragFlags: Int, swipeFlags: Int): Int {
        //锁定元素状态
        val groupId = adapter!!.getGroupForPosition(viewHolder.adapterPosition)
//        if (groupId == 0 && SettingsLockUtil.isFuncationLocked(context, PAYTYPE_ORDER)) {
//            return 0
//        }
        return if (!adapter!!.isGroupHeaderForPosition(viewHolder.adapterPosition)) {
            ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        } else 0
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        //得到拖动ViewHolder的position
        val fromPosition = viewHolder.adapterPosition
        Log.i("TAG", "onMove--fromPosition-$fromPosition")
        //得到目标ViewHolder的position
        val toPosition = target.adapterPosition
        Log.i("TAG", "onMove--toPosition-$toPosition")
        if (this.adapter!!.isGroupHeaderForPosition(toPosition)) {
            return false
        }

        if (this.adapter!!.getGroupForPosition(fromPosition) == this.adapter!!.getGroupForPosition(toPosition)) {
            val groupId = adapter!!.getGroupForPosition(fromPosition)
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(this.adapter!!.data[groupId].moreChildBeanList, adapter!!.getChildItemForPosition(i), adapter!!.getChildItemForPosition(i + 1))
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(this.adapter!!.data[groupId].moreChildBeanList, adapter!!.getChildItemForPosition(i), adapter!!.getChildItemForPosition(i - 1))
                }
            }
            this.adapter!!.notifyItemMoved(fromPosition, toPosition)
            return true
        } else {
            return false
        }
    }

    /**
     * 拖拽的时候开始调用
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder!!.adapterPosition == adapter!!.getChildItemForPosition(viewHolder.adapterPosition)) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
                //获取系统震动服务
                val vib = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                //震动70毫秒
                vib.vibrate(100)
                super.onSelectedChanged(viewHolder, actionState)
            }
        }
    }

    /**
     * 拖拽完成时调用
     */
    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder.adapterPosition > 0 && viewHolder.adapterPosition == adapter!!.getChildItemForPosition(viewHolder.adapterPosition)) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffffff"))
            super.clearView(recyclerView, viewHolder)
            if (onDragListener != null) {
                onDragListener!!.onFinishDrag()
            }
        } else {
            if (onDragListener != null) {
                onDragListener!!.onFinishDrag()
                super.clearView(recyclerView, viewHolder)
            }
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    fun setOnDragListener(onDragListener: OnDragListener): RecyclerItemMovedTouchHelperCallBack {
        this.onDragListener = onDragListener
        return this
    }

    /**
     * 拖拽接口
     */
    interface OnDragListener {
        fun onFinishDrag()
    }
}
