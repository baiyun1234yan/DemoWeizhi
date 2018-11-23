package bai.bai.bai.demo.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import bai.bai.bai.demo.R
import bai.bai.bai.demo.recycler.adapter.MoreAdapter
import com.snappay.cashier.basemodule.bean.MoreChildBean
import com.snappay.cashier.basemodule.bean.MoreGroupBean
import com.snappay.cashier.bizmodule.main.*
import com.snappay.cashier.bizmodule.main.recycler.RecyclerGridLayoutManager
import com.snappay.cashier.bizmodule.main.recycler.RecyclerItemMovedTouchHelperCallBack
import com.snappay.cashier.bizmodule.main.recycler.RecyclerItemMovedTouchListener

class MorePopupWindow constructor(context: Context) : View(context)
        , RecyclerItemMovedTouchHelperCallBack.OnDragListener
        , View.OnClickListener
        , View.OnTouchListener {

    var mAdapter: MoreAdapter? = null
    var mItemTouchHelpRvGroup: ItemTouchHelper? = null
    var mMoreList: List<MoreGroupBean>? = null

    private var mContentView: RelativeLayout? = null//整个布局
    private var mRvMore: RecyclerView? = null
    private var mPopupWindow: PopupWindow? = null

    private var mSreenHeight: Int? = 0//整个屏幕的高度
    private var mHeight: Int = 0//布局的原始高度
    private var mCurrentHeight: Int? = 0//滑动图标时当前高度
    private var mRangeHeight: Int? = 0//区间高度


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showWindow() {
        initData()
        initView()
    }

    /**
     * 获得数据
     */
    private fun initData() {
        mMoreList = listOf(MoreGroupBean("账目查询", listOf(
                MoreChildBean("main_more_transaction_record", "交易记录", "main_select_tip_choose_bg", 11, 0)
                , MoreChildBean("main_more_day_summary", "日汇总", "main_select_tip_choose_bg", 12, 2)
                , MoreChildBean("main_more_quick_query", "快速查单", "main_select_tip_choose_bg", 13, 1)
                , MoreChildBean("main_more_tip_summary", "小费汇总", "main_select_tip_choose_bg", 14, 3)
        ))
                , MoreGroupBean("银行卡业务", listOf(
                MoreChildBean("main_more_batch_collect", "批结算", "main_select_tip_choose_bg", 21, 0)
                , MoreChildBean("main_more_preauthorization", "预授权", "main_select_tip_choose_bg", 22, 1)
                , MoreChildBean("main_more_preauthorization_record", "预授权记录", "main_select_tip_choose_bg", 23, 2)
        ))
                , MoreGroupBean("系统设置", listOf(
                MoreChildBean("main_more_settings", "设置", "main_select_tip_choose_bg", 31, 0)
                , MoreChildBean("main_more_about", "关于", "main_select_tip_choose_bg", 32, 1)
                , MoreChildBean("main_more_logout", "退出登录", "main_select_tip_choose_bg", 33, 2)
        ))
        )
    }

    /**
     * 初始化控件
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.main_popup_window_more, null)
        contentView.findViewById<RelativeLayout>(R.id.rl_cross).setOnClickListener(this)
        contentView.findViewById<ImageView>(R.id.iv_space).setOnClickListener(this)
        contentView.findViewById<RelativeLayout>(R.id.rl_slide).setOnTouchListener(this)
        mContentView = contentView.findViewById(R.id.rl_content)
        mRvMore = contentView.findViewById(R.id.rv_group)
        mAdapter = MoreAdapter(context, this!!.mMoreList!!)

        initRecyclerView()
        initPopupWindow(contentView)
    }

    /**
     * 初始化recyclerView设置
     */
    private fun initRecyclerView() {
        val manager = RecyclerGridLayoutManager(context, mAdapter!!, 4)//recyclerView实现网格形式
        manager.setSpeedRatio(0.5)
        mRvMore!!.layoutManager = manager
        mRvMore!!.isLayoutFrozen = true//锁定recyclerView界面，不刷新
        mRvMore!!.isNestedScrollingEnabled = true//嵌套滑动
        mRvMore!!.adapter = mAdapter
        mItemTouchHelpRvGroup = ItemTouchHelper(RecyclerItemMovedTouchHelperCallBack(mAdapter!!, context).setOnDragListener(this))
        mItemTouchHelpRvGroup!!.attachToRecyclerView(mRvMore)

        mRvMore!!.addOnItemTouchListener(object : RecyclerItemMovedTouchListener(this!!.mRvMore!!, this!!.mAdapter!!) {
            override fun onItemClick(vh: RecyclerView.ViewHolder, groupPosition: Int, childPosition: Int) {
                this@MorePopupWindow.onItemClick(groupPosition, childPosition)
            }

            override fun onLongClick(vh: RecyclerView.ViewHolder, position: Int) {
                if (mItemTouchHelpRvGroup != null) {
                    mItemTouchHelpRvGroup!!.startDrag(vh)
                }
            }
        })
    }

    /**
     * 初始化popupWindow设置
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initPopupWindow(contentView: View) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mSreenHeight = windowManager.defaultDisplay.height
        var width = windowManager.defaultDisplay.width
        mHeight = (mSreenHeight!! * 967.0 / 1280.0).toInt()
        mRangeHeight = (mSreenHeight!! * 100.0 / 1280.0).toInt()

        mPopupWindow = PopupWindow(contentView, width, mHeight + this!!.mRangeHeight!!)
        mPopupWindow!!.isFocusable = true
        mPopupWindow!!.isOutsideTouchable = true
        mPopupWindow!!.setBackgroundDrawable(context.getDrawable(R.drawable.select_key_board_num))
        mPopupWindow!!.animationStyle = R.style.BasePopupWindow
        mPopupWindow!!.showAtLocation(contentView, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
        mPopupWindow!!.showAsDropDown(mRvMore)
        setViewHeight(this!!.mContentView!!, mHeight)
    }

    /**
     * 为每一个item设置监听
     */
    private fun onItemClick(groupPosition: Int, childPosition: Int) {
        when (mMoreList!!.get(groupPosition).moreChildBeanList.get(childPosition).tag) {
//            BUSINESS_ID_TRANSACTION_RECORD -> {//交易记录
//                ToastManager.toast(context, "交易记录")
//            }
//            BUSINESS_ID_DAY_SUMMARY -> {//日汇总
//                ToastManager.toast(context, "日汇总")
//            }
//            BUSINESS_ID_QUICK_QUERY -> {//快速查单
//                ToastManager.toast(context, "快速查单")
//            }
//            BUSINESS_ID_TIP_SUMMARY -> {//小费汇总
//                ToastManager.toast(context, "小费汇总")
//            }
//            BUSINESS_ID_BATCH_SUMMARY -> {//批结算
//                ToastManager.toast(context, "批结算")
//            }
//            BUSINESS_ID_PREAUTHORIZATION -> {//预授权
//                ToastManager.toast(context, "预授权")
//            }
//            BUSINESS_ID_PREAUTHORIZATION_RECORD -> {//预授权记录
//                ToastManager.toast(context, "预授权记录")
//            }
//            BUSINESS_ID_SETTINGS -> {//设置
//                ToastManager.toast(context, "设置")
//            }
//            BUSINESS_ID_ABOUT -> {//关于
//                ToastManager.toast(context, "关于")
//            }
//            BUSINESS_ID_LOGOUT -> {//退出登录
//                ToastManager.toast(context, "退出登录")
//            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_cross -> {
                mPopupWindow!!.dismiss()
            }
            R.id.iv_space -> {
                mPopupWindow!!.dismiss()
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mCurrentHeight = mSreenHeight!! - event!!.rawY.toInt()
        when (event!!.action) {
            MotionEvent.ACTION_MOVE -> {
                setViewHeight(this!!.mContentView!!, mCurrentHeight!!)
                mContentView!!.postInvalidate()

                if (mHeight - this!!.mRangeHeight!! > mCurrentHeight!!) {//超过滑动边界，popupWindow取消
                    mPopupWindow!!.dismiss()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mCurrentHeight!! < mHeight && mHeight - this!!.mRangeHeight!! < mCurrentHeight!!) {//在一定范围内，恢复初始高度
                    setViewHeight(this!!.mContentView!!, mHeight)
                }
            }
        }
        return true
    }

    /**
     * 拖拽完成后
     */
    override fun onFinishDrag() {

    }

    /**
     * 设置子控件的高度
     */
    fun setViewHeight(view: View, height: Int) {
        var params = view.layoutParams as RelativeLayout.LayoutParams
        params.height = height
        view.layoutParams = params
    }

}