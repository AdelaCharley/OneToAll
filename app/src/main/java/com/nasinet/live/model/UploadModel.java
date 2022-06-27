package com.nasinet.live.model;

import com.nasinet.live.contract.UploadContract;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.net.RetrofitClient;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

public class UploadModel implements UploadContract.Model {



    @Override
    public Flowable<BaseResponse> publish(RequestBody body) {
        return RetrofitClient.getInstance().getApi().publish(body);
    }
}
