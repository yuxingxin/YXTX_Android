package com.yuxingxin.yxtx.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Sean on 15/7/22.
 */
public class DeviceUtils {

    /**
     * Returns the screen width in pixels
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return dm.widthPixels;
    }
}
