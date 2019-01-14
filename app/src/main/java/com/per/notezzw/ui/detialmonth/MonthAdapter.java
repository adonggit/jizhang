package com.per.notezzw.ui.detialmonth;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.per.notezzw.R;
import com.per.notezzw.bean.MsgMonth;

import java.util.List;

/**
 * Created by 22762 on 2016/1/7.
 */
public class MonthAdapter extends BaseAdapter {
    private int mPosition = 0;
    private List<MsgMonth> mData;
    private Context mContext;

    public MonthAdapter(List<MsgMonth> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = LinearLayout.inflate(mContext, R.layout.lv_left_item, null);
            textView = (TextView) convertView.findViewById(R.id.lv_item_day);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }
        MsgMonth msgMonth = mData.get(position);

        convertView.setBackgroundColor(mContext.getResources().getColor(R.color.view_background_light));
        if (mPosition == position) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.background_activity));
        }
        if (msgMonth.getTotalin() - msgMonth.getTotalout() < 0) {
            textView.setTextColor(mContext.getResources().getColor(R.color.text_out_color));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.text_in_color));
        }
        textView.setText(msgMonth.getYear() + "/" + msgMonth.getMonth());
        return convertView;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
