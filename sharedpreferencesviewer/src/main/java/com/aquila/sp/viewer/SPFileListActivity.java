package com.aquila.sp.viewer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.aquila.sp.viewer.presenter.BaseFileContract;
import com.aquila.sp.viewer.presenter.SPFilePresenter;
import com.aquila.sp.viewer.utils.CustomRecyclerView;


public class SPFileListActivity extends BaseActivity implements BaseFileContract.view, OnClickListener{
    private ImageView backImageView;
    private CustomRecyclerView customRecyclerView;
    private FileListAdapter adapter;
    private static boolean isReleaseCanView;

    BaseFileContract.Presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_list_layout);
        backImageView = findViewById(R.id.activity_sp_list_back_ImageView);
        customRecyclerView = findViewById(R.id.activity_sp_list_content_RecyclerView);

        backImageView.setOnClickListener(this);

        presenter = new SPFilePresenter(this);


        adapter = new FileListAdapter(this);
        adapter.setOnItemDeleteListener(onItemDeleteListener);
        customRecyclerView.setAdapter(adapter);

        updateData();
    }


    public void updateData(){
        adapter.setData(presenter.getSPFileList());

        if (adapter.getItemCount() <= 0){
            findViewById(R.id.activity_sp_empty_view).setVisibility(View.VISIBLE);
            customRecyclerView.setVisibility(View.GONE);
        }else{
            findViewById(R.id.activity_sp_empty_view).setVisibility(View.GONE);
            customRecyclerView.setVisibility(View.VISIBLE);
        }
    }




    private OnDataUpdateListener onItemDeleteListener = new OnDataUpdateListener() {
        @Override
        public void onDataUIUpdate() {
            updateData();
        }
    };


    @Override
    public void onClick(View v) {
        if (v == backImageView){
            onBackPressed();
        }
    }


    private static boolean isDebugMode(Context context){
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /****
     * 设置在release模式下是否开启SP的查看
     * @param flag
     */
    public static void setIsReleaseCanJump(boolean flag){
        isReleaseCanView = flag;
    }

    public static void gotoSPFileListActivity(Context context){
        if (isDebugMode(context) || isReleaseCanView){
            Intent intent = new Intent(context, SPFileListActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public String getAPPPackageName() {
        return getPackageName();
    }
}
