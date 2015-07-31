package com.yuxingxin.yxtx.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Typeface creation.
 *
 * Created by Sean on 15/7/22.
 */
public class FactoryTypeface{

    public static Typeface createTypeface(Context context, int typeface){
        return Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s.ttf", context.getString(typeface)));
    }
}
