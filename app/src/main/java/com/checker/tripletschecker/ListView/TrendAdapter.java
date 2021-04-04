package com.checker.tripletschecker.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.checker.tripletschecker.R;

import java.util.ArrayList;

public class TrendAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ListData> trend;

    public TrendAdapter(Context context, ArrayList<ListData> data) {
        mContext = context;
        trend = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return trend.size();
    }

    @Override
    public Object getItem(int i) {
        return trend.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.activity_listview, null);

        TextView date = (TextView) view.findViewById(R.id.v_date);
        TextView count1 = (TextView) view.findViewById(R.id.v_first_count);
        TextView count2 = (TextView) view.findViewById(R.id.v_second_count);
        TextView count3 = (TextView) view.findViewById(R.id.v_third_count);
        TextView count4 = (TextView) view.findViewById(R.id.v_fourth_count);

        TextView start_time = (TextView) view.findViewById(R.id.v_start_time);

        TextView end_duration1 = (TextView) view.findViewById(R.id.v_first_duration);
        TextView end_duration2 = (TextView) view.findViewById(R.id.v_second_duration);
        TextView end_duration3 = (TextView) view.findViewById(R.id.v_third_duration);
        TextView end_duration4 = (TextView) view.findViewById(R.id.v_fourth_duration);

        date.setText(trend.get(i).getM_date());
        count1.setText(trend.get(i).getM_count1());
        count2.setText(trend.get(i).getM_count2());
        count3.setText(trend.get(i).getM_count3());
        count4.setText(trend.get(i).getM_count4());

        start_time.setText(trend.get(i).getM_start_time());

        end_duration1.setText(trend.get(i).getM_duration1());
        end_duration2.setText(trend.get(i).getM_duration2());
        end_duration3.setText(trend.get(i).getM_duration3());
        end_duration4.setText(trend.get(i).getM_duration4());

        return view;
    }
}
