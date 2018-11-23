package bai.bai.bai.demo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import bai.bai.bai.demo.R;
import bai.bai.bai.demo.utils.SharedUtil;

/**
 * @author stt
 * 时间：2016.06.19
 * 说明：popwindow视图
 */
public class PopWindow {
    private PopupWindow mPopupWindow;
    private ListView mLvNumber;
    private PopWindowAdapter mAdapter;

    /**
     * 下拉菜单（PopupWindow）
     */
    public void showWindow(final Activity activity, View parent, List<String> datas, final EditText editText) {
        View layoutView;
        if (mPopupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutView = layoutInflater.inflate(R.layout.popup_window_number_list, null);
            // 创建一个PopuWidow对象,第二个参数是宽，第三个参数是高
            WindowManager windowManager1 = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            int wight = windowManager1.getDefaultDisplay().getWidth()-82;
            int height = windowManager1.getDefaultDisplay().getWidth();
            mPopupWindow = new PopupWindow(layoutView, wight, height);

            mLvNumber = (ListView) layoutView.findViewById(R.id.lv_user_number);

            mAdapter = new PopWindowAdapter(activity, datas);
            mLvNumber.setAdapter(mAdapter);
        }
        // 使其聚集
        mPopupWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
//        int xPos = windowManager.getDefaultDisplay().getWidth() / 2 - mPopupWindow.getWidth() / 2;
//        Log.i("coder", "xPos:" + xPos);
        //第一个参数是控件，第二个是左边的位置，第三个是上边的位置
//        mPopupWindow.showAsDropDown(parent, 0, 0);
        mPopupWindow.showAsDropDown(parent);//在某个布局正下面

        mLvNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                editText.setText(mAdapter.getItem(position).toString());

                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }



    private class PopWindowAdapter extends BaseAdapter {

        private List<String> mNumbers;
        private Context mContext;
        private LayoutInflater inflater;

        public PopWindowAdapter(Context context, List<String> numbers) {
            this.mContext = context;
            this.mNumbers = getNumbers(numbers);
            inflater = LayoutInflater.from(context);
        }

        private List<String> getNumbers(List<String>  numbers) {
            if (numbers == null) {
                numbers = new ArrayList<>();
            }
            return numbers;
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
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_popup_window_number_list, null);
                holder.tvUserNumber = (TextView) convertView.findViewById(R.id.tv_user_number);
                holder.viewLine = (View) convertView.findViewById(R.id.view_number_list_line);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String number = mNumbers.get(position);
            holder.tvUserNumber.setText(number);
//            if(position == 0){
//                holder.viewLine.setVisibility(View.GONE);
//            }
            return convertView;
        }

        private class ViewHolder {
            private TextView tvUserNumber;
            private View viewLine;
        }

    }


}
