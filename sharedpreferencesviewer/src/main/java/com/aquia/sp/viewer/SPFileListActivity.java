package com.aquia.sp.viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.aquia.sp.viewer.utils.CustomRecyclerView;

import java.io.File;

/**
 * Created by yulong_wang on 2017/11/9 17:40.
 */

public class SPFileListActivity extends BaseActivity implements OnClickListener{
    private ImageView backImageView;
    private CustomRecyclerView customRecyclerView;
    private FileListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_list_layout);
        backImageView = findViewById(R.id.activity_sp_list_back_ImageView);
        customRecyclerView = findViewById(R.id.activity_sp_list_content_RecyclerView);

        backImageView.setOnClickListener(this);
        adapter = new FileListAdapter(this);
        adapter.setOnItemDeleteListener(onItemDeleteListener);
        customRecyclerView.setAdapter(adapter);

        updateData();
    }


    public void updateData(){
        adapter.setData(getSPFileList());
    }



    private File[] getSPFileList() {
        File root = new File("/data/data/"+getPackageName()+"/shared_prefs");
        if (root.isDirectory()) {
            return root.listFiles();
        }
        return null;
    }

    FileListAdapter.OnItemDeleteListener onItemDeleteListener = new FileListAdapter.OnItemDeleteListener() {
        @Override
        public void onItemDelete() {
            updateData();
        }
    };


    @Override
    public void onClick(View v) {
        if (v == backImageView){
            onBackPressed();
        }
    }


}
