package com.snappay.cashier.basemodule.bean

import com.snappay.cashier.basemodule.bean.MoreChildBean
import java.io.Serializable
import java.util.ArrayList

/**
 * 更多界面：每个组（主布局）的实体类
 */

class MoreGroupBean : Serializable {
    var title: String//列表标题
    var moreChildBeanList: List<MoreChildBean>

    constructor(title: String, moreChildBeanList: List<MoreChildBean>) {
        this.title = title
        this.moreChildBeanList = moreChildBeanList
    }

    constructor(title: String) {
        this.title = title
        moreChildBeanList = ArrayList()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
