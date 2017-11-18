package com.aquila.sp.viewer.presenter;


import java.io.File;

public interface BaseFileContract {

    interface view{
        String getAPPPackageName();
    }


    interface Presenter{
        File[] getSPFileList();
    }



}
