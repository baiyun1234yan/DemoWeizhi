package com.snappay.cashier.bizmodule.main.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.snappay.cashier.basemodule.bean.MoreGroupBean


/**
 * Created by Arrow on 2016/9/22.
 */

abstract class RecyclerViewAdapter<H : RecyclerView.ViewHolder, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var mGroupPosition: IntArray? = null//组Group的position索引集合
    private var mChildPositionWithinGroup: IntArray? = null//通过组获取的组内子布局Child的索引集合
    private var mIsHeader: BooleanArray? = null//是否是组Group的header
    private var mCount = 0//组数 + 子布局数

    abstract val data: List<MoreGroupBean>

    /**
     * Returns the number of group in the RecyclerView 获得组Group的数量
     */
    protected abstract val groupCount: Int

    init {
        registerAdapterDataObserver(GroupDataObserver())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setupIndices()
    }

    /**
     * 安排数据
     */
    private fun setupIndices() {
        mCount = countItems()
        allocateAuxiliaryArrays(mCount) //根据count分配子数组
        precomputeIndices()
    }

    /**
     * 获得所有项的数量（组数 + 子布局数）
     */
    private fun countItems(): Int {
        var mCount = 0
        val groupCounts = groupCount

        for (i in 0 until groupCounts) {
            mCount += 1 + getChildCountForGroup(i)
        }
        return mCount
    }

    /**
     * 为组、子布局、是否是header设置范围
     */
    private fun allocateAuxiliaryArrays(mCount: Int) {
        mGroupPosition = IntArray(mCount)
        mChildPositionWithinGroup = IntArray(mCount)
        mIsHeader = BooleanArray(mCount)
    }

    /**
     * 设置每一项的性质（设置每一项数据是 组Group的属性 还是 子布局的属性）
     */
    private fun precomputeIndices() {
        var index = 0
        val groupCounts = groupCount

        for (i in 0 until groupCounts) {
            setPrecomputedItem(index, true, i, 0)
            index++
            for (j in 0 until getChildCountForGroup(i)) {
                setPrecomputedItem(index, false, i, j)
                index++
            }
        }
    }

    /**
     * 设置预计算子项
     */
    private fun setPrecomputedItem(index: Int, mIsHeader: Boolean, group: Int, position: Int) {
        this.mIsHeader!![index] = mIsHeader
        mGroupPosition!![index] = group
        mChildPositionWithinGroup!![index] = position
    }

    /**
     * 通过position判断是否为组Group的header
     */
    fun isGroupHeaderForPosition(position: Int): Boolean {
        if (mIsHeader == null) {
            setupIndices()
        }
        return mIsHeader!![position]
    }

    /**
     * 判断是否为组Group的header
     */
    protected fun isGroupHeaderViewType(viewType: Int): Boolean {
        return viewType == TYPE_GROUP_HEADER
    }

    override fun getItemViewType(position: Int): Int {
        if (mGroupPosition == null) {
            setupIndices()
        }
        return if (isGroupHeaderForPosition(position)) {
            TYPE_GROUP_HEADER
        } else {
            TYPE_CHILD_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        if (isGroupHeaderViewType(viewType)) {
            viewHolder = onCreateGroupHeaderViewHolder(parent, viewType)
        } else {
            viewHolder = onCreateChildViewHolder(parent, viewType)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val group = mGroupPosition!![position]
        val index = mChildPositionWithinGroup!![position]

        if (isGroupHeaderForPosition(position)) {
            onBindGroupHeaderViewHolder(holder as H, group)
        } else {
            onBindChildViewHolder(holder as VH, group, index)
        }
    }

    override fun getItemCount(): Int {
        return mCount
    }

    /**
     * Returns the number of items for a given group 获得每个组Group的子布局数量
     */
    protected abstract fun getChildCountForGroup(group: Int): Int

    /**
     * Creates a MyGroupHeaderViewHolder of class H for a Header 添加组Group的标题
     */
    protected abstract fun onCreateGroupHeaderViewHolder(parent: ViewGroup, viewType: Int): H

    /**
     * Creates a MyChildViewHolder of class VH for an Item 添加子布局
     */
    protected abstract fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): VH

    /**
     * Binds data to the header view of a given group 填充组Group的header数据
     */
    protected abstract fun onBindGroupHeaderViewHolder(holder: H, group: Int)

    /**
     * Binds data to the item view for a given position within a group  填充子布局数据
     */
    protected abstract fun onBindChildViewHolder(holder: VH, group: Int, position: Int)

    internal inner class GroupDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            setupIndices()
        }
    }

    /**
     * 通过position获得子布局Child
     */
    fun getChildItemForPosition(position: Int): Int {
        return mChildPositionWithinGroup!![position]
    }

    /**
     * 通过position获得组Group
     */
    fun getGroupForPosition(position: Int): Int {
        return mGroupPosition!![position]
    }

    companion object {
        protected const val TYPE_GROUP_HEADER = -1
        protected const val TYPE_CHILD_ITEM = -2
    }

}
