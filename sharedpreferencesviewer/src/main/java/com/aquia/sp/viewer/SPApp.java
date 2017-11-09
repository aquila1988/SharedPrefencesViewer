package com.aquia.sp.viewer;

import android.app.Application;

/**
 * Created by yulong on 2017/11/9.
 */

public class SPApp {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    public static void init(Application application){
        instance = application;
    }

}
