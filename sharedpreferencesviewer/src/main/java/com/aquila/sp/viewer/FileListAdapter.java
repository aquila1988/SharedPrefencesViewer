package com.aquila.sp.viewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.NameListViewHolder> {
    private File[] files;
    private Activity activity;

    public FileListAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(File[] files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @Override
    public NameListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NameListViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_sp_list_item_layout, null));
    }

    @Override
    public void onBindViewHolder(final NameListViewHolder holder, int position) {
        final File file = files[position];
        holder.fileNameTextView.setText(file.getName().replace(".xml", ""));

        holder.fileNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SPValueListActivity.class);
                intent.putExtra(SPValueListActivity.EXTRA_sp_name, holder.fileNameTextView.getText().toString());
                activity.startActivity(intent);
            }
        });
        holder.fileNameTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteFileDialog(file);
                return false;
            }


        });
    }

    private void showDeleteFileDialog(final File file) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("您确定删除[" + file.getName().replace(".xml", "") + "]吗？删除后文件不可恢复。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        file.delete();
                        if (onItemDeleteListener != null){
                            onItemDeleteListener.onDataUIUpdate();
                        }

                    }
                }).setNegativeButton("取消", null)
                .create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        if (files == null) {
            return 0;
        }
        return files.length;
    }

    OnDataUpdateListener onItemDeleteListener;

    public void setOnItemDeleteListener(OnDataUpdateListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public static class NameListViewHolder extends RecyclerView.ViewHolder{
        public TextView fileNameTextView;
        public NameListViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.adapter_list_sp_name_TextView);
        }
    }
}
