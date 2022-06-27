package com.nasinet.nasinet.interfaces;

import com.nasinet.nasinet.base.NasiSDK;

/**
 * Created by cxf on 2017/8/11.
 */

public abstract class CommonCallback<T> {
    public abstract void callback(T bean);

}
