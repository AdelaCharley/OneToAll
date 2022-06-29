package com.nasinet.live.util;

import android.content.res.Resources;

import com.nasinet.live.base.MyApp;


/**
 * Created by cxf on 2017/10/10.
 * 获取string.xml中的字
 */

public class WordUtil {

    private static Resources sResources;

    static {
        sResources = MyApp.getInstance().getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }
}
