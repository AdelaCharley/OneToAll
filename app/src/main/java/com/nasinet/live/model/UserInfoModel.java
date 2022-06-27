package com.nasinet.live.model;

import com.nasinet.live.contract.UserInfoContract;
import com.nasinet.live.model.entity.Banners;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.net.RetrofitClient;

import io.reactivex.Flowable;

public class UserInfoModel implements UserInfoContract.Model {

    @Override
    public Flowable<BaseResponse<Banners>> getUserPage() {
        return  RetrofitClient.getInstance().getApi().getUserPage();
    }
}
