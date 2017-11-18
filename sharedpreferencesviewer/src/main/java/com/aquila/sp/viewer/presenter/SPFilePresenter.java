package com.aquila.sp.viewer.presenter;

import java.io.File;

/**
 * Created by yulong on 2017/11/18.
 */

public class SPFilePresenter implements BaseFileContract.Presenter {
    BaseFileContract.view parentView;

    public SPFilePresenter(BaseFileContract.view view){
        parentView = view;
    }

    @Override
    public File[] getSPFileList() {
        File root = new File("/data/data/"+ parentView.getAPPPackageName()+"/shared_prefs");
        if (root.isDirectory()) {
            return root.listFiles();
        }
        return null;
    }
}
