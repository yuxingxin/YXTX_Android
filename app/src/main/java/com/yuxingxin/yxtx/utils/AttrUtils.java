package com.yuxingxin.yxtx.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by Sean on 15/7/22.
 */
public class AttrUtils {

    /**
     * Returns the size in pixels of an attribute dimension
     * @param context
     * @param attr
     * @return
     */
    public static int getThemeAttributeDimensionSize(Context context,int attr){
        TypedArray a = null;
        try{
            a = context.getTheme().obtainStyledAttributes(new int[] { attr });
            return a.getDimensionPixelSize(0, 0);
        }finally{
            if(a != null)
            {
                a.recycle();
            }
        }
    }
}
