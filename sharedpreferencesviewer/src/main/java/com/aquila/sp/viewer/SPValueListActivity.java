package com.aquila.sp.viewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquila.sp.viewer.utils.CustomRecyclerView;


public class SPValueListActivity extends BaseActivity implements View.OnClickListener {
    static final String EXTRA_sp_name ="sp_name";
    private ImageView backImageView;
    private ImageView addImageView;
    private TextView titleTextView;
    private CustomRecyclerView customRecyclerView;
    private ValueListAdapter valueListAdapter;
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_values_layout);
        backImageView = findViewById(R.id.activity_value_back_ImageView);
        titleTextView = findViewById(R.id.activity_value_title_name_TextView);
        addImageView = findViewById(R.id.activity_value_add_ImageView);

        customRecyclerView = findViewById(R.id.activity_value_content_RecyclerView);

        backImageView.setOnClickListener(this);
        addImageView.setOnClickListener(this);

        String fileName = getIntent().getStringExtra(EXTRA_sp_name);
        titleTextView.setText(fileName);

        sp = getSharedPreferences(fileName, MODE_PRIVATE);


        valueListAdapter = new ValueListAdapter(this, fileName);

        customRecyclerView.setAdapter(valueListAdapter);
    }

    private void showAddParamsDialog(){
        SPDetailDialog spDetailDialog = new SPDetailDialog(this,sp);
        spDetailDialog.show();
        spDetailDialog.setTitleText("新建键值对");
        spDetailDialog.setIsCreate(true);

        spDetailDialog.setOnDataUpdateListener(onDataUpdateListener);
    }


    OnDataUpdateListener onDataUpdateListener = new OnDataUpdateListener() {
        @Override
        public void onDataUIUpdate() {
            if (valueListAdapter != null){
                valueListAdapter.updateUI();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v == addImageView){
            showAddParamsDialog();
        }
        if (v == backImageView){
            onBackPressed();
        }
    }


}
