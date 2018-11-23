package bai.bai.bai.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import bai.bai.bai.demo.R;

public class KeyBoardLinearLayout extends LinearLayout implements AdapterView.OnItemClickListener, View.OnClickListener {

    private int mItemHeight;//行高
    private float mItemTextSize;//item文字大小
    private int mKeyBoardType;//键盘类型
    private KeyBoardNumAdapter mAdapter;

    private static final int TYPE_CALCULATION = 1;//主界面键盘
    private static final int TYPE_PASSWORD = 2;//输入密码
    private static final int TYPE_OTHER = 3;//乱序

    private KeyBoartTouchListener mKeyBoartTouchListener;


    public KeyBoardLinearLayout(Context context) {
        this(context, null);
    }

    public KeyBoardLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.keyboard_linearlayout, null);
        setLinearLayoutStyle(context, attrs, view);
        addView(view);

    }

    private void setLinearLayoutStyle(Context context, AttributeSet attrs, View view) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KeyBoardLinearLayout);
        mItemHeight = array.getDimensionPixelSize(R.styleable.KeyBoardLinearLayout_itemHeight, 100);
        mItemTextSize = array.getDimension(R.styleable.KeyBoardLinearLayout_itemTextSize, 30);
        mKeyBoardType = array.getInt(R.styleable.KeyBoardLinearLayout_keyBoardType, TYPE_CALCULATION);

        GridView gvKeyBoardNum = (GridView) view.findViewById(R.id.gr_keyboard_num);
        gvKeyBoardNum.setOnItemClickListener(this);
        mAdapter = new KeyBoardNumAdapter(context, getNumbers(mKeyBoardType));
        gvKeyBoardNum.setAdapter(mAdapter);

        LinearLayout llKeyBoardEmpty = (LinearLayout) view.findViewById(R.id.ll_keyboard_del);
        llKeyBoardEmpty.setOnClickListener(this);

        TextView tvKeyBoardOk = (TextView) view.findViewById(R.id.tv_keyboard_ok);
        tvKeyBoardOk.setOnClickListener(this);
        tvKeyBoardOk.setTextSize(mItemTextSize + 2);
    }

    /**
     * 获得键盘上要填入的数据
     */
    private List<String> getNumbers(int keyBoardType) {
        List<String> numbers = new ArrayList<>();
        switch (keyBoardType) {
            case TYPE_CALCULATION:
                numbers = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"});
                break;
            case TYPE_PASSWORD:
                numbers = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "C"});
                break;
            case TYPE_OTHER:
                numbers = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"});
                Collections.shuffle(numbers);//乱序
                numbers.set(numbers.indexOf("C"), numbers.get(11));
                numbers.set(11, "C");
                break;
            default:
                numbers = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"});
                break;
        }

        return numbers;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_keyboard_del:
                mKeyBoartTouchListener.onDelTouch();
                break;
            case R.id.tv_keyboard_ok:
                mKeyBoartTouchListener.onOkTouch();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mKeyBoartTouchListener.onNumberTouch(mAdapter.getItem(position) + "");
    }

    public interface KeyBoartTouchListener {
        void onNumberTouch(String inputNumber);

        void onDelTouch();

        void onOkTouch();
    }

    public void setKeyBoartTouchListener(KeyBoartTouchListener listener) {
        this.mKeyBoartTouchListener = listener;
    }

    private class KeyBoardNumAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mNumbers;
        private LayoutInflater inflater;

        public KeyBoardNumAdapter(Context mContext, List<String> mNumbers) {
            this.mContext = mContext;
            this.mNumbers = getNumbers(mNumbers);
            this.inflater = LayoutInflater.from(mContext);
        }

        private List<String> getNumbers(List<String> mNumbers) {
            if (mNumbers == null) {
                mNumbers = new ArrayList<>();
            }
            return mNumbers;
        }

        @Override
        public int getCount() {
            return mNumbers.size();
        }

        @Override
        public Object getItem(int position) {
            return mNumbers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler;
            if (convertView == null) {
                hodler = new ViewHodler();
                convertView = inflater.inflate(R.layout.item_key_button, null);
                hodler.mTvStyle = (TextView) convertView.findViewById(R.id.tv_style);
                hodler.mTvStyle.setTextSize(mItemTextSize);

                convertView.setTag(hodler);
            } else {
                hodler = (ViewHodler) convertView.getTag();
            }
            setViewHeight(hodler.mTvStyle);

            String number = mNumbers.get(position);
            hodler.mTvStyle.setText(number);

            if (position == 11) {
                hodler.mTvStyle.setTextColor(Color.BLUE);
            }

            return convertView;
        }

        /**
         * 设置子控件的高度
         */
        private void setViewHeight(View view) {
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.height = mItemHeight;
            view.setLayoutParams(params);
        }

        private class ViewHodler {
            private TextView mTvStyle;

        }

    }


}
