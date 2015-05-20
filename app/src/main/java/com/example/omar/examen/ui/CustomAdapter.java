package com.example.omar.examen.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.omar.examen.POJO;
import com.example.omar.examen.R;

import java.util.ArrayList;

/**
 * Created by omar on 30/03/2015.
 */
public class CustomAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<POJO> mList;
    private LayoutInflater mLayoutInflater = null;
    public CustomAdapter(Context context, ArrayList<POJO> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }
        viewHolder.mName.setText(mList.get(position).getName());
        return v;
    }

    class CompleteListViewHolder {
        public TextView mName;
        public CompleteListViewHolder(View base) {
            mName = (TextView) base.findViewById(R.id.name);
        }
    }


}
