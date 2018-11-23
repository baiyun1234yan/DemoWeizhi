package bai.bai.bai.demo.kt

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

import bai.bai.bai.demo.R

class KeyBoardLinearLayout constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), AdapterView.OnItemClickListener, View.OnClickListener {

    private var mItemHeight: Int = 0//行高
    private var mItemTextSize: Float = 0.toFloat()//item文字大小
    private var mKeyBoardType: Int = 0//键盘类型
    private var mAdapter: KeyBoardNumAdapter? = null

    private var mKeyBoartTouchListener: KeyBoartTouchListener? = null

    init {

        val view = LayoutInflater.from(context).inflate(R.layout.keyboard_linearlayout, null)
        setLinearLayoutStyle(context, attrs, view)
        addView(view)

    }

    private fun setLinearLayoutStyle(context: Context, attrs: AttributeSet?, view: View) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.KeyBoardLinearLayout)
        mItemHeight = array.getDimensionPixelSize(R.styleable.KeyBoardLinearLayout_itemHeight, 100)
        mItemTextSize = array.getDimension(R.styleable.KeyBoardLinearLayout_itemTextSize, 30f)
        mKeyBoardType = array.getInt(R.styleable.KeyBoardLinearLayout_keyBoardType, TYPE_CALCULATION)

        val gvKeyBoardNum = view.findViewById<View>(R.id.gr_keyboard_num) as GridView
        gvKeyBoardNum.onItemClickListener = this
        mAdapter = KeyBoardNumAdapter(context, getNumbers(mKeyBoardType))
        gvKeyBoardNum.adapter = mAdapter

        val llKeyBoardEmpty = view.findViewById<View>(R.id.ll_keyboard_del) as LinearLayout
        llKeyBoardEmpty.setOnClickListener(this)

        val tvKeyBoardOk = view.findViewById<View>(R.id.tv_keyboard_ok) as TextView
        tvKeyBoardOk.setOnClickListener(this)
        tvKeyBoardOk.textSize = mItemTextSize + 2
    }

    /**
     * 获得键盘上要填入的数据
     */
    private fun getNumbers(keyBoardType: Int): List<String> {
        var numbers: MutableList<String>? = null
        when (keyBoardType) {
            TYPE_CALCULATION -> numbers = Arrays.asList(*arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"))
            TYPE_PASSWORD -> numbers = Arrays.asList(*arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "C"))
            TYPE_OTHER -> {
                numbers = Arrays.asList(*arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", " ", "0", "C"))
                Collections.shuffle(numbers)//乱序
                numbers[numbers.indexOf(" ")] = numbers[10]
                numbers[10] = " "
                numbers[numbers.indexOf("C")] = numbers[11]
                numbers[11] = "C"
            }
            else -> numbers = Arrays.asList(*arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "C"))
        }

        return numbers
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_keyboard_del -> mKeyBoartTouchListener!!.onDelTouch()
            R.id.tv_keyboard_ok -> mKeyBoartTouchListener!!.onOkTouch()
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        mKeyBoartTouchListener!!.onNumberTouch(mAdapter!!.getItem(position).toString() + "")
    }

    interface KeyBoartTouchListener {
        fun onNumberTouch(inputNumber: String)

        fun onDelTouch()

        fun onOkTouch()
    }

    fun setKeyBoartTouchListener(listener: KeyBoartTouchListener) {
        this.mKeyBoartTouchListener = listener
    }

    private inner class KeyBoardNumAdapter(private val mContext: Context, mNumbers: List<String>) : BaseAdapter() {
        private val mNumbers: List<String>
        private val inflater: LayoutInflater

        init {
            this.mNumbers = getNumbers(mNumbers)
            this.inflater = LayoutInflater.from(mContext)
        }

        private fun getNumbers(mNumbers: List<String>?): List<String> {
            var mNumbers = mNumbers
            if (mNumbers == null) {
                mNumbers = ArrayList()
            }
            return mNumbers
        }

        override fun getCount(): Int {
            return mNumbers.size
        }

        override fun getItem(position: Int): Any {
            return mNumbers[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val hodler: ViewHodler
            if (convertView == null) {
                hodler = ViewHodler()
                convertView = inflater.inflate(R.layout.item_key_button, null)
                hodler.mTvStyle = convertView!!.findViewById<View>(R.id.tv_style) as TextView
                hodler.mTvStyle!!.textSize = mItemTextSize

                convertView.tag = hodler
            } else {
                hodler = convertView.tag as ViewHodler
            }
            setViewHeight(hodler.mTvStyle!!)

            val number = mNumbers[position]
            hodler.mTvStyle!!.text = number

            if (position == 11) {
                hodler.mTvStyle!!.setTextColor(resources.getColor(R.color.colorAccent))//清除按钮
            }

            return convertView
        }

        /**
         * 设置子控件的高度
         */
        private fun setViewHeight(view: View) {
            val params = view.layoutParams as LinearLayout.LayoutParams
            params.height = mItemHeight
            view.layoutParams = params
        }

        private inner class ViewHodler {
            var mTvStyle: TextView? = null

        }

    }

    companion object {
        private val TYPE_CALCULATION = 1//主界面键盘
        private val TYPE_PASSWORD = 2//输入密码
        private val TYPE_OTHER = 3//乱序
    }


}
