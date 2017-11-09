package com.aquia.sp.viewer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquia.sp.viewer.utils.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yulong on 2017/11/9.
 */

public class SPValueListActivity extends BaseActivity implements View.OnClickListener {
    static final String EXTRA_sp_name ="sp_name";
    private ImageView backImageView;
    private TextView titleTextView;
    private CustomRecyclerView customRecyclerView;
    private ValueListAdapter valueListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_values_layout);
        backImageView = findViewById(R.id.activity_value_back_ImageView);
        titleTextView = findViewById(R.id.activity_value_title_name_TextView);
        customRecyclerView = findViewById(R.id.activity_value_content_RecyclerView);

        backImageView.setOnClickListener(this);

        valueListAdapter = new ValueListAdapter(this);
        customRecyclerView.setAdapter(valueListAdapter);

        String fileName = getIntent().getStringExtra(EXTRA_sp_name);

        titleTextView.setText(fileName);

        SharedPreferences sp = getSharedPreferences(fileName, MODE_PRIVATE);
        valueListAdapter.setAllData(sp.getAll());

    }

    @Override
    public void onClick(View v) {
        if (v == backImageView){
            onBackPressed();
        }
    }


    private static class ValueListAdapter extends RecyclerView.Adapter<SPValueViewHolder>{
        private Activity activity;
        private ArrayList<String> keyList = new ArrayList<>();
        private ArrayList<Object> values = new ArrayList<>();

        public ValueListAdapter(Activity activity){
            this.activity = activity;
        }

        public void setAllData(Map<String, ?> allValuesMap){
            keyList.clear();
            values.clear();
            if (allValuesMap != null){
                for (String key: allValuesMap.keySet()){
                    keyList.add(key);
                    values.add(allValuesMap.get(key));
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public SPValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SPValueViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_sp_value_item_layout, null));
        }

        @Override
        public void onBindViewHolder(SPValueViewHolder holder, int position) {
            holder.keyEditText.setText(keyList.get(position));
            holder.valueEditText.setText(values.get(position).toString());
        }

        @Override
        public int getItemCount() {
            if (keyList == null){
                return 0;
            }
            return keyList.size();
        }
    }





    private static class SPValueViewHolder extends RecyclerView.ViewHolder{
        public TextView keyEditText;
        public TextView valueEditText;
        public SPValueViewHolder(View itemView) {
            super(itemView);
            keyEditText = itemView.findViewById(R.id.adapter_sp_key_EditText);
            valueEditText = itemView.findViewById(R.id.adapter_sp_value_EditText);
        }
    }



}
