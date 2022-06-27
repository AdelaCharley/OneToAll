package com.nasinet.live.util;

import android.view.Gravity;
import android.widget.Toast;

import com.nasinet.live.base.MyApp;

public class ToastUtils {


    public static void showT(String text) {
        Toast mToast = Toast.makeText(MyApp.getInstance(), null, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mToast.setText(text);
        mToast.show();
    }
}
