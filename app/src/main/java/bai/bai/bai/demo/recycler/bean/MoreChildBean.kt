package com.snappay.cashier.basemodule.bean

import java.io.Serializable

/**
 * 更多界面：具体项(子布局)的实体类
 */
class MoreChildBean(var imageIdSource: String?//图标名称（mipmap文件夹下）
                    , var title: String?//具体项的标题
                    , drawableSource: String, var tag: Int//每一项的唯一标识
                    , var position: Int//所处的位置
) : Serializable, Comparable<MoreChildBean> {
    var drawableSource: String? = null
        private set//图标名称（drawable文件夹下）

    init {
        this.drawableSource = drawableSource
    }

    fun setDrawable(drawableSource: String) {
        this.drawableSource = drawableSource
    }

    override fun compareTo(another: MoreChildBean): Int {
        return this.position - another.position
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}

