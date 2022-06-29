package com.nasinet.live.model;

import com.nasinet.live.contract.MainContract;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.net.RetrofitClient;

import io.reactivex.Flowable;

public class MainModel implements MainContract.Model {


    @Override
    public Flowable<BaseResponse<UserConfig>> getUserConfig() {
        return RetrofitClient.getInstance().getApi().getUserConfig();
    }
}
