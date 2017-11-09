package com.aquia.sp.viewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        customRecyclerView.setAdapter(adapter);

        adapter.setData(getSPFileList());

    }





    private File[] getSPFileList() {
        File root = new File("/data/data/"+getPackageName()+"/shared_prefs");
        if (root.isDirectory()) {
            return root.listFiles();
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        if (v == backImageView){
            onBackPressed();
        }
    }







    private static class FileListAdapter extends RecyclerView.Adapter<NameListViewHolder>{
        private File[] files;
        private Activity activity;
        public FileListAdapter(Activity activity){
            this.activity = activity;
        }
        public void setData(File[] files){
            this.files = files;
        }

        @Override
        public NameListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NameListViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_sp_list_item_layout, null));
        }

        @Override
        public void onBindViewHolder(final NameListViewHolder holder, int position) {
            final File file = files[position];
            holder.fileNameTextView.setText(file.getName().replace(".xml",""));

            holder.fileNameTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SPValueListActivity.class);
                    intent.putExtra(SPValueListActivity.EXTRA_sp_name, holder.fileNameTextView.getText().toString());
                    activity.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            if (files == null){
                return 0;
            }
            return files.length;
        }
    }



    private static class NameListViewHolder extends RecyclerView.ViewHolder{
        public TextView fileNameTextView;
        public NameListViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.adapter_list_sp_name_TextView);
        }
    }



}
