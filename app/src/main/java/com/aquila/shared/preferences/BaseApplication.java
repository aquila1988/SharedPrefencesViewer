package com.aquila.shared.preferences;

import android.app.Application;

/**
 * Created by yulong_wang on 2017/11/9 17:38.
 */

public class BaseApplication extends Application {

    private Application application;

    public Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }




}
