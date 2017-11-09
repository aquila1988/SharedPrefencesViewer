package com.aquia.sp.viewer;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aquia.sp.viewer.utils.CLog;
import com.aquia.sp.viewer.utils.CustomRecyclerView;

import java.io.File;

/**
 * Created by yulong_wang on 2017/11/9 17:40.
 */

public class SPListActivity extends BaseActivity implements OnClickListener{
    private ImageView backImageView;
    private CustomRecyclerView customRecyclerView;

    private FileListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_list_layout);
        backImageView = findViewById(R.id.activity_sp_list_back_ImageView);
        customRecyclerView = findViewById(R.id.activity_sp_list_content_RecyclerView);

        adapter = new FileListAdapter();
        customRecyclerView.setAdapter(adapter);

        initFileList();

    }


    private void initFileList() {
        String path ;
        File file = Environment.getDataDirectory();
        if (file != null){
            CLog.debug(file.getAbsolutePath());

            if (file.listFiles() != null){
                for (File f: file.listFiles()){
                    CLog.debug(f.getAbsolutePath() + f.getName());
                }

            }


        }

    }


    @Override
    public void onClick(View v) {
        if (v == backImageView){
            onBackPressed();
        }
    }







    private static class FileListAdapter extends RecyclerView.Adapter<NameListViewHolder>{

        @Override
        public NameListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(NameListViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }



    private static class NameListViewHolder extends RecyclerView.ViewHolder{

        public NameListViewHolder(View itemView) {
            super(itemView);
        }
    }



}
