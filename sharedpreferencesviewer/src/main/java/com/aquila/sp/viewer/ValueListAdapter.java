package com.aquila.sp.viewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aquila.sp.viewer.utils.TypeConst;

import java.util.ArrayList;
import java.util.Map;

public class ValueListAdapter extends RecyclerView.Adapter<ValueListAdapter.SPValueViewHolder> {
    private Activity activity;
    private ArrayList<String> keyList = new ArrayList<>();
    private ArrayList<Object> values = new ArrayList<>();
    private SharedPreferences sp;
    public ValueListAdapter(Activity activity, String fileName) {
        this.activity = activity;
        sp = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        updateUI();
    }

    public void updateUI() {
        Map<String, ?> allValuesMap = sp.getAll();

        keyList.clear();
        values.clear();
        if (allValuesMap != null) {
            for (String key : allValuesMap.keySet()) {
                keyList.add(key);
                values.add(allValuesMap.get(key));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public SPValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SPValueViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_sp_value_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SPValueViewHolder holder, final int position) {
        holder.keyTextView.setText(keyList.get(position));
        final Object obj = values.get(position);

        holder.valueTextView.setText(obj.toString());
        holder.typeTextView.setText(TypeConst.getObjectType(obj));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContentDetailDialog(keyList.get(position), obj);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteFileDialog(keyList.get(position));
                return false;
            }
        });

    }

    private SPDetailDialog spDetailDialog;
    private void showContentDetailDialog(String key, Object obj){
        if (spDetailDialog == null){
            spDetailDialog = new SPDetailDialog(activity, sp);
            spDetailDialog.setOnDataUpdateListener(onDataUpdateListener);
        }
        spDetailDialog.show();
        spDetailDialog.initData(key, obj);
    }


    OnDataUpdateListener onDataUpdateListener = new OnDataUpdateListener() {
        @Override
        public void onDataUIUpdate() {
            updateUI();
        }
    };

    private void showDeleteFileDialog(final String key) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("您确定删除[" + key+ "]吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sp.edit().remove(key).commit();
                        updateUI();
                    }
                }).setNegativeButton("取消", null)
                .create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        if (keyList == null) {
            return 0;
        }
        return keyList.size();
    }

    public static class SPValueViewHolder extends RecyclerView.ViewHolder{
        public TextView keyTextView;
        public TextView valueTextView;
        public TextView typeTextView;
        public SPValueViewHolder(View itemView) {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.adapter_sp_key_TextView);
            valueTextView = (TextView) itemView.findViewById(R.id.adapter_sp_value_TextView);
            typeTextView = (TextView) itemView.findViewById(R.id.adapter_sp_type_TextView);
        }
    }
}
