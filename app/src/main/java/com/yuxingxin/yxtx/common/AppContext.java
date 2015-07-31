package com.yuxingxin.yxtx.common;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Sean on 15/7/26.
 */
public class AppContext extends Application{

    private static AppContext appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static AppContext getAppContext(){
        return appContext;
    }
}
