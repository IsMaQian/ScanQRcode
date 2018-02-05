package com.example.mqtest.scanqrcode.Transform;

import android.content.Context;

/**
 * Created by Administrator on 2018/2/2.
 */

public class DensityUtil {
    public static int dip2dp(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
