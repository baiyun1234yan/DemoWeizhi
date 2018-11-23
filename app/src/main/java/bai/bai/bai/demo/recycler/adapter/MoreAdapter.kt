package bai.bai.bai.demo.recycler.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import bai.bai.bai.demo.R
import bai.bai.bai.demo.recycler.util.ReflectUtil.getResourceByReflect

import com.snappay.cashier.bizmodule.main.recycler.RecyclerViewAdapter
import com.snappay.cashier.basemodule.bean.MoreGroupBean

/**
 * 更多界面的适配器
 */
class MoreAdapter(private val context: Context, protected var mDataList: List<MoreGroupBean>) : RecyclerViewAdapter<MoreAdapter.MyGroupHeaderViewHolder, MoreAdapter.MyChildViewHolder>() {
    private val mInflater: LayoutInflater

    override val data: List<MoreGroupBean>
        get() = mDataList

    override val groupCount: Int
        get() = if (isEmpty(mDataList)) 0 else mDataList.size

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun getChildCountForGroup(group: Int): Int {
        val count = mDataList[group].moreChildBeanList.size
        return if (isEmpty(mDataList[group].moreChildBeanList)) 0 else count
    }

    override fun onCreateGroupHeaderViewHolder(parent: ViewGroup, viewType: Int): MyGroupHeaderViewHolder {
        return MyGroupHeaderViewHolder(mInflater.inflate(R.layout.main_recycler_item_more_group_header, parent, false))
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): MyChildViewHolder {
        return MyChildViewHolder(mInflater.inflate(R.layout.main_recycler_item_more_child, parent, false))
    }

    override fun onBindGroupHeaderViewHolder(holder: MyGroupHeaderViewHolder, group: Int) {
        holder.tv_header.text = mDataList[group].title
    }

    override fun onBindChildViewHolder(holder: MyChildViewHolder, group: Int, position: Int) {
        holder.textView.text = mDataList[group].moreChildBeanList[position].title!!.toString()
        holder.imageView.setBackgroundResource(getResourceByReflect(mDataList[group].moreChildBeanList[position].imageIdSource!!, R.mipmap::class.java))
    }

    /**
     * 每个子布局children
     */
    inner class MyChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        val imageView: ImageView

        init {
            this.textView = itemView.findViewById<View>(R.id.tv_item_position) as TextView
            this.imageView = itemView.findViewById<View>(R.id.iv_item_position) as ImageView
        }
    }

    /**
     * 每个组的标题header
     */
    inner class MyGroupHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_header: TextView

        init {
            this.tv_header = itemView.findViewById<View>(R.id.tv_item_header_position) as TextView
        }
    }

    companion object {
        fun <D> isEmpty(list: List<D>?): Boolean {
            return list == null || list.isEmpty()
        }
    }

}


