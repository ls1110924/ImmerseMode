package com.yunxian.immersemode.sample.application;

import android.app.Application;

import com.yunxian.immerse.manager.GlobalConfig;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/1/27 17:33
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalConfig.init(this);
    }
}
